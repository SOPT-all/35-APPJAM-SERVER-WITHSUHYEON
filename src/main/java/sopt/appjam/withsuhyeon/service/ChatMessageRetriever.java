package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import sopt.appjam.withsuhyeon.domain.ChatMessage;
import sopt.appjam.withsuhyeon.repository.ChatMessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageRetriever {
    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getChatMessagesByChatRoomId(String chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId);
    }
}
