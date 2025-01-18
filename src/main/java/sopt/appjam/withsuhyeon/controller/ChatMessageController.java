package sopt.appjam.withsuhyeon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sopt.appjam.withsuhyeon.anotation.UserId;
import sopt.appjam.withsuhyeon.dto.chat.res.ChatMessagesResponse;
import sopt.appjam.withsuhyeon.service.ChatMessageService;

@RestController
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    @GetMapping("/api/v1/chatrooms/{chatRoomId}")
    public ResponseEntity<ChatMessagesResponse> getMessagesInChatRoom(
            @UserId Long userId,
            @PathVariable String chatRoomId
    ) {
        return ResponseEntity.ok(
                chatMessageService.getChatMessagesInChatRoomByUserId(userId, chatRoomId)
        );
    }
}
