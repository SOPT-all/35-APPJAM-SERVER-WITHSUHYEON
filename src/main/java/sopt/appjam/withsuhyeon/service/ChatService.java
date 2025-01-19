package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import sopt.appjam.withsuhyeon.domain.ChatRoom;
import sopt.appjam.withsuhyeon.domain.ChatRoomInfo;
import sopt.appjam.withsuhyeon.dto.chat.req.ChatRequest;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatMessageSaver chatMessageSaver;
    private final ChatRoomRetriever chatRoomRetriever;
    private final ChatRoomInfoRetriever chatRoomInfoRetriever;

    public void saveMessageRealTime(
            Long senderId,
            Long receiverId,
            String content,
            String ownerChatRoomId,
            String peerChatRoomId
    ) {
        ChatRoom chatRoom = chatRoomRetriever.findByChatRoomId(ownerChatRoomId);
        ChatRoomInfo chatRoomInfo = chatRoomInfoRetriever.findByRoomNumber(chatRoom.getRoomNumber());

        // 내 채팅방에 메시지 저장하기
        chatMessageSaver.save(
                senderId,
                receiverId,
                content,
                Boolean.TRUE,
                ownerChatRoomId
        );

        // 상대 채팅방에 메시지 저장하기
        chatMessageSaver.save(
                senderId,
                receiverId,
                content,
                chatRoomInfo.getParticipatingUsers().contains(receiverId), // 상대방이 채팅방에 접속해 있는지 확인하기
                peerChatRoomId
        );
    }
}
