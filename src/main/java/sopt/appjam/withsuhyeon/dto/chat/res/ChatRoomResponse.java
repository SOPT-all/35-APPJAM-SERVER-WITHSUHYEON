package sopt.appjam.withsuhyeon.dto.chat.res;

import lombok.Builder;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@Builder
public record ChatRoomResponse(
        String ownerChatRoomId,
        String peerChatRoomId,
        Long postId,
        Long chatOwnerId,
        Long chatPeerId,
        String chatPeerNickname,
        String chatPeerProfileImage,
        String lastChatMessage,
        LocalDateTime lastChatAt,
        Long unReadCount,
        String postTitle,
        String postPlace,
        String postCost
) {
}