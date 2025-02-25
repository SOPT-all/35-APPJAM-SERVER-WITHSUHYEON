package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import sopt.appjam.withsuhyeon.domain.ChatRoom;
import sopt.appjam.withsuhyeon.domain.ChatRoomInfo;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.chat.req.ChatRequest;
import sopt.appjam.withsuhyeon.repository.ChatRoomInfoRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomInfoService {
    private final UserRetriever userRetriever;
    private final ChatRoomRetriever chatRoomRetriever;
    private final ChatRoomInfoRetriever chatRoomInfoRetriever;
    private final ChatMessageService chatMessageService;
    private final ChatRoomInfoRepository chatRoomInfoRepository;

    public void userJoinChatRoom(Long userId, String chatRoomId) {
        UserEntity user = userRetriever.findByUserId(userId);
        ChatRoom chatRoom = chatRoomRetriever.findByChatRoomId(chatRoomId);
        ChatRoomInfo chatRoomInfo = chatRoomInfoRetriever.findByRoomNumber(chatRoom.getRoomNumber());

        log.info(user.getNickname() + " 유저가 " + chatRoom.getId() + " 방으로 입장했습니다.");

        chatRoomInfo.addUser(user.getId());
        chatRoomInfoRepository.save(chatRoomInfo); // 채팅방에 유저가 들어간 상황을 저장합니다.

        chatMessageService.handleChatMessageRead(chatRoomId);
    }

    public void userExitChatRoom(Long userId, String chatRoomId) {
        UserEntity user = userRetriever.findByUserId(userId);
        ChatRoom chatRoom = chatRoomRetriever.findByChatRoomId(chatRoomId);
        ChatRoomInfo chatRoomInfo = chatRoomInfoRetriever.findByRoomNumber(chatRoom.getRoomNumber());

        log.info(user.getNickname() + " 유저가 " + chatRoom.getId() + " 방에서 퇴장했습니다.");

        chatRoomInfo.removeUser(user.getId());
        chatRoomInfoRepository.save(chatRoomInfo); // 채팅방에 유저가 들어간 상황을 저장합니다.
    }

    public void updateLastChat(String ownerChatRoomId, String content) {
        ChatRoom chatRoom = chatRoomRetriever.findByChatRoomId(ownerChatRoomId);
        ChatRoomInfo chatRoomInfo = chatRoomInfoRetriever.findByRoomNumber(chatRoom.getRoomNumber());

        chatRoomInfo.updateInfo(content);
        chatRoomInfoRepository.save(chatRoomInfo);
    }
}
