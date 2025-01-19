package sopt.appjam.withsuhyeon.dto.chat.req;

public record ChatRequest(
        String ownerChatRoomId,
        String peerChatRoomId,
        Long senderId,
        Long receiverId,
        Long postId,
        String content,
        String type
) {
}
