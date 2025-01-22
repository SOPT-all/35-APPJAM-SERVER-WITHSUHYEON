package sopt.appjam.withsuhyeon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import sopt.appjam.withsuhyeon.domain.ChatRoom;
import sopt.appjam.withsuhyeon.domain.ChatRoomInfo;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.chat.req.ChatRequest;
import sopt.appjam.withsuhyeon.dto.chat.res.ChatCreateResponse;
import sopt.appjam.withsuhyeon.dto.chat.res.ChatResponse;
import sopt.appjam.withsuhyeon.dto.chat.res.ChatRoomsResponse;
import sopt.appjam.withsuhyeon.repository.ChatRoomInfoRepository;
import sopt.appjam.withsuhyeon.repository.ChatRoomRepository;
import sopt.appjam.withsuhyeon.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final UserRetriever userRetriever;
    private final ChatRoomRetriever chatRoomRetriever;
    private final ChatRoomInfoRetriever chatRoomInfoRetriever;
    private final ChatRoomSavor chatRoomSavor;
    private final ChatService chatService;
    private final ChatRoomService chatRoomService;
    private final ChatRoomInfoService chatRoomInfoService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomInfoRepository chatRoomInfoRepository;

    // WebSocket 세션 관리
    private static final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long userId = getUserIdFromSession(session);

        sessions.put(userId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        try {
            // JSON 메시지 파싱
            ChatRequest incomingMessage = objectMapper.readValue(textMessage.getPayload(), ChatRequest.class);

            log.info(incomingMessage.ownerChatRoomId().toString());
            log.info(incomingMessage.peerChatRoomId().toString());
            log.info(incomingMessage.receiverId().toString());
            log.info(incomingMessage.content());
            log.info(incomingMessage.type());
            log.info(sessions.keySet().toString());

            // sender 은 소켓 메시지를 보낸 사람, receiver 은 해당 메시지를 받은 사람.
            UserEntity sender = userRetriever.findByUserId(incomingMessage.senderId());
            UserEntity receiver = userRetriever.findByUserId(incomingMessage.receiverId());

            // 채팅방 생성이랑 메세지 전송 구분 : 채팅방 생성은 게시글 조회에서 이루어진다.
            if(incomingMessage.type().equals("CREATE")) {

                if(chatRoomRepository.existsByPostIdAndOwnerIdAndPeerId(
                        incomingMessage.postId(),
                        incomingMessage.senderId(),
                        incomingMessage.receiverId()
                )) {
                    return; // 채팅방이 존재하는 경우 CREATE 는 잘못된 요청입니다.
                }

                // 소켓에 연결중인 유저의 세션을 가져옵니다.
                WebSocketSession senderSession = sessions.get(incomingMessage.senderId());
                WebSocketSession receiverSession = sessions.get(incomingMessage.receiverId());

                // 채팅방 생성
                List<String> newChatRooms = chatRoomSavor.createChatRoom(
                        incomingMessage.postId(),
                        incomingMessage.senderId(),
                        incomingMessage.receiverId(),
                        incomingMessage.content()
                );

                // 내 세션으로 보내는 정보 : 생성된 내 채팅방의 ID, 생성된 상대방 채팅방 ID
                senderSession.sendMessage(new TextMessage(
                        objectMapper.writeValueAsString(new ChatCreateResponse(
                                "CHAT_ROOM_CREATED",
                                newChatRooms.get(0).toString(), // 생성된 내 채팅방 ID
                                newChatRooms.get(1).toString() // 생성된 상대방 채팅방 ID
                        ))
                ));
                if(receiverSession != null && receiverSession.isOpen()) {
                    log.info("상대방이 세션에 있음");
                    // 채팅방 생성 시 상대방은 채팅방에 들어와 있을 수가 없음
                    ChatRoomsResponse chatRoomsResponses = chatRoomService.getChatRooms(receiver.getId());

                    receiverSession.sendMessage(new TextMessage(
                            objectMapper.writeValueAsString(chatRoomsResponses)
                    ));
                } else {
                    log.info("상대방이 세션에 없음");
                }
            } else if(incomingMessage.type().equals("MESSAGE")) {
                // 소켓에 연결중인 유저의 세션을 가져옵니다.
                WebSocketSession receiverSession = sessions.get(incomingMessage.receiverId());

                ChatRoom chatRoom = chatRoomRetriever.findByChatRoomId(incomingMessage.ownerChatRoomId());
                ChatRoomInfo chatRoomInfo = chatRoomInfoRetriever.findByRoomNumber(chatRoom.getRoomNumber());

                // 상대방이 세션에 들어와 있을 때
                if(receiverSession != null && receiverSession.isOpen()) {
                    log.info("상대방이 세션에 있음");
                    if (chatRoomInfo.getParticipatingUsers().contains(receiver.getId())) {
                        // 케이스 1-1. 상대방이 채팅방에 들어와 있을 때
                        try {
                            chatService.saveMessageRealTime(
                                    incomingMessage.senderId(),
                                    incomingMessage.receiverId(),
                                    incomingMessage.content(),
                                    incomingMessage.ownerChatRoomId(),
                                    incomingMessage.peerChatRoomId()
                            );
                            chatRoomInfoService.updateLastChat(
                                    incomingMessage.ownerChatRoomId(),
                                    incomingMessage.content()
                            );

                            ChatResponse chatResponse = new ChatResponse(incomingMessage.content(), LocalDateTime.now());
                            receiverSession.sendMessage(new TextMessage(
                                    objectMapper.writeValueAsString(chatResponse)
                            ));
                        } catch (Exception e) {
                            log.error("힝 ㅠ"); // 에러 로그 추가
                        }
                    } else {
                        // 케이스 1-2. 상대방이 채팅방에 들어와 있지 않을 때
                        try {
                            log.info("채팅 메세지 저장 시작");
                            chatService.saveMessageRealTime(
                                    incomingMessage.senderId(),
                                    incomingMessage.receiverId(),
                                    incomingMessage.content(),
                                    incomingMessage.ownerChatRoomId(),
                                    incomingMessage.peerChatRoomId()
                            );

                            log.info("채팅 메세지 라스트 메시지 저장 시작");
                            chatRoomInfoService.updateLastChat(
                                    incomingMessage.ownerChatRoomId(),
                                    incomingMessage.content()
                            );

                            ChatRoomsResponse chatRoomsResponses = chatRoomService.getChatRooms(receiver.getId());

                            receiverSession.sendMessage(new TextMessage(
                                    objectMapper.writeValueAsString(chatRoomsResponses)
                            ));
                        } catch (Exception e) {
                            log.error("힝 ㅠㅠ"); // 에러 로그 추가
                        }
                    }
                } else {
                    log.info("상대방이 세션에 없음");

                    log.info("채팅 메세지 저장 시작");
                    chatService.saveMessageRealTime(
                            incomingMessage.senderId(),
                            incomingMessage.receiverId(),
                            incomingMessage.content(),
                            incomingMessage.ownerChatRoomId(),
                            incomingMessage.peerChatRoomId()
                    );

                    log.info("채팅 메세지 라스트 메시지 저장 시작");
                    chatRoomInfoService.updateLastChat(
                            incomingMessage.ownerChatRoomId(),
                            incomingMessage.content()
                    );
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long userId = getUserIdFromSession(session);
        // 1. 현재 세션 목록에서 제거
        sessions.remove(userId);

        // 2. DB에서 유저가 참여하고 있을 수 있는 모든 ChatRoom 조회
        //    - 보통 ownerId = userId 이거나 peerId = userId 인 경우가 해당 유저가 관련된 방
        //    - repository 메서드명과 쿼리는 상황에 맞춰 수정 필요
        List<ChatRoom> chatRooms = chatRoomRepository.findByOwnerIdOrPeerId(userId, userId);

        // 3. 조회된 각 ChatRoom에 대해, 해당 ChatRoomInfo(동일 roomNumber 사용)를 찾고 participatingUsers에서 제거
        for (ChatRoom chatRoom : chatRooms) {
            UUID roomNumber = chatRoom.getRoomNumber();
            try {
                ChatRoomInfo chatRoomInfo = chatRoomInfoRepository.findByRoomNumber(roomNumber)
                        .orElseThrow();

                if (chatRoomInfo != null) {
                    // 만약 해당 유저가 참여중이면 제거
                    if (chatRoomInfo.isUserParticipate(userId)) {
                        chatRoomInfo.removeUser(userId);
                        // 변경사항 반영
                        chatRoomInfoRepository.save(chatRoomInfo);
                    }
                }
            } catch (Exception e) {
                log.error(e.toString());
            }

        }
    }

    private Long getUserIdFromSession(WebSocketSession session) {
        return Long.parseLong(session.getUri().getQuery().split("=")[1]);
    }
}

