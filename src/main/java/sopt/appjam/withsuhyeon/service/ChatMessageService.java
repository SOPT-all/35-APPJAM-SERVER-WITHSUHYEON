package sopt.appjam.withsuhyeon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sopt.appjam.withsuhyeon.domain.ChatMessage;
import sopt.appjam.withsuhyeon.domain.UserEntity;
import sopt.appjam.withsuhyeon.dto.chat.res.ChatMessageResponse;
import sopt.appjam.withsuhyeon.dto.chat.res.ChatMessagesResponse;
import sopt.appjam.withsuhyeon.repository.ChatMessageRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final UserRetriever userRetriever;
    private final ChatMessageRetriever chatMessageRetriever;
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessagesResponse getChatMessagesInChatRoomByUserId(Long userId, String chatRoomId) {
        UserEntity user = userRetriever.findByUserId(userId);

        List<ChatMessage> chatMessages = chatMessageRepository.findByChatRoomId(chatRoomId);

        return  new ChatMessagesResponse(chatMessages.stream().map(
                chatMessage -> new ChatMessageResponse(
                        chatMessage.getSenderId().equals(user.getId()) ? "SEND" : "RECEIVE",
                        chatMessage.getContent(),
                        chatMessage.getTimestamp(),
                        chatMessage.getIsRead()
                )
        ).toList());
    }

    // 해당 채팅방의 메시지를 모두 읽음 처리 합니다.
    public void handleChatMessageRead(String chatRoomId) {
        List<ChatMessage> chatMessages = chatMessageRetriever.getChatMessagesByChatRoomId(chatRoomId);

        // 각각의 채팅을 업데이트 합니다.
        for (ChatMessage message : chatMessages) {
            message.updateIsRead(true); // 읽음 처리
            chatMessageRepository.save(message); // 저장
        }
    }
}
