package sopt.appjam.withsuhyeon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sopt.appjam.withsuhyeon.anotation.UserId;
import sopt.appjam.withsuhyeon.service.ChatRoomInfoService;

@RestController
@RequiredArgsConstructor
public class ChatRoomInfoController {
    private final ChatRoomInfoService chatRoomInfoService;

    @PatchMapping("/api/v1/chatrooms/join/{chatRoomId}")
    public void userJoinChatRoom(
            @UserId Long userId,
            @PathVariable String chatRoomId
    ) {
        chatRoomInfoService.userJoinChatRoom(userId, chatRoomId);
    }

    @PatchMapping("/api/v1/chatrooms/exit/{chatRoomId}")
    public void userExitChatRoom(
            @UserId Long userId,
            @PathVariable String chatRoomId
    ) {
        chatRoomInfoService.userExitChatRoom(userId, chatRoomId);
    }
}
