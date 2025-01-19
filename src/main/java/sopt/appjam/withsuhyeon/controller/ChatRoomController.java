package sopt.appjam.withsuhyeon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sopt.appjam.withsuhyeon.anotation.UserId;
import sopt.appjam.withsuhyeon.dto.chat.res.ChatRoomsResponse;
import sopt.appjam.withsuhyeon.service.ChatRoomService;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/api/v1/chatrooms")
    public ResponseEntity<ChatRoomsResponse> getChatRooms(
            @UserId Long userId
    ) {
        return ResponseEntity.ok(chatRoomService.getChatRooms(userId));
    }
}
