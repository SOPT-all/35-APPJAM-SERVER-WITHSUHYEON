package sopt.appjam.withsuhyeon.dto.chat.res;

public record ChatCreateResponse(
        String type,
        String ownerChatRoomId,
        String peerChatRoomId
) {
}
