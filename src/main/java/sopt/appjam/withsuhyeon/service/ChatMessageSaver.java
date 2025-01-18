package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import sopt.appjam.withsuhyeon.domain.ChatMessage;
import sopt.appjam.withsuhyeon.repository.ChatMessageRepository;

@Service
@RequiredArgsConstructor
public class ChatMessageSaver {
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessage save(Long senderId, Long receiverId, String content, Boolean isRead, String chatRoomId) {
        ChatMessage chatMessage = ChatMessage.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .content(content)
                .isRead(isRead)
                .chatRoomId(chatRoomId).build();

        return chatMessageRepository.save(chatMessage);
    }
}
