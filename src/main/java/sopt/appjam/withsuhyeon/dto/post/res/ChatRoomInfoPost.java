package sopt.appjam.withsuhyeon.dto.post.res;

import lombok.Builder;

@Builder
public record ChatRoomInfoPost(
        Long postId,
        Long ownerId,
        Long writerId,
        String ownerChatRoomId,
        String peerChatRoomId
) {
}
