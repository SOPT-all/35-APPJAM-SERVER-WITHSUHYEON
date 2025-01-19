package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import sopt.appjam.withsuhyeon.domain.ChatRoom;
import sopt.appjam.withsuhyeon.domain.ChatRoomInfo;
import sopt.appjam.withsuhyeon.dto.chat.req.ChatRequest;
import sopt.appjam.withsuhyeon.repository.ChatMessageRepository;
import sopt.appjam.withsuhyeon.repository.ChatRoomInfoRepository;
import sopt.appjam.withsuhyeon.repository.ChatRoomRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomSavor {
    private final UserRetriever userRetriever;
    private final ChatRoomInfoRetriever chatRoomInfoRetriever;
    private final ChatMessageSaver chatMessageSaver;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomInfoRepository chatRoomInfoRepository; // 코드 분리 필요함
    private final ChatMessageRepository chatMessageRepository; // 코드 분리 필요함

    public List<String> createChatRoom(
            Long postId,
            Long senderId,
            Long receiverId,
            String content
    ) {
        UUID roomId = UUID.randomUUID();

        ChatRoom ownerChatRoom = ChatRoom.builder()
                .roomNumber(roomId)
                .postId(postId)
                .ownerId(senderId)
                .peerId(receiverId).build();
        chatRoomRepository.save(ownerChatRoom);

        ChatRoom peerChatRoom = ChatRoom.builder()
                .roomNumber(roomId)
                .postId(postId)
                .ownerId(receiverId)
                .peerId(senderId).build();
        chatRoomRepository.save(peerChatRoom);


        ChatRoomInfo chatRoomInfo = ChatRoomInfo.builder()
                .roomNumber(roomId)
                .lastMessage(content)
                .lastChatAt(LocalDateTime.now()).build();
        chatRoomInfoRepository.save(chatRoomInfo);

        chatMessageSaver.save(
                senderId,
                receiverId,
                content,
                Boolean.TRUE,
                ownerChatRoom.getId()
        ); // 내 채팅방에 새로운 메시지 저장

        chatMessageSaver.save(
                senderId,
                receiverId,
                content,
                chatRoomInfo.getParticipatingUsers().contains(receiverId),
                peerChatRoom.getId()
        ); // 상대 체팅방에 새로운 메시지 저장

        List<String> chatRoomIds = new ArrayList<>();
        chatRoomIds.add(ownerChatRoom.getId()); // myChatRoom ID 추가
        chatRoomIds.add(peerChatRoom.getId()); // oppChatRoom ID 추가

        return chatRoomIds;
    }
}
