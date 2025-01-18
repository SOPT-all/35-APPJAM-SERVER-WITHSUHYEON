package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import sopt.appjam.withsuhyeon.domain.ChatRoom;
import sopt.appjam.withsuhyeon.exception.ChatRoomErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.repository.ChatRoomRepository;

@Service
@RequiredArgsConstructor
public class ChatRoomRetriever {
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom findByChatRoomId(String chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> BaseException.type(ChatRoomErrorCode.CHAT_ROOM_NOT_FOUND));
    }
}
