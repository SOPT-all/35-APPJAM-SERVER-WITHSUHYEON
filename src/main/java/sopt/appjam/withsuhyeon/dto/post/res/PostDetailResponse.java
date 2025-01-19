package sopt.appjam.withsuhyeon.dto.post.res;

import lombok.Builder;

@Builder
public record PostDetailResponse(
        String title,
        String content,
        String nickname,
        String createdAt,
        String profileImage,
        Integer price,
        Boolean owner,
        Boolean matching,
        PostDetailInfo postDetailInfo,
        ChatRoomInfoPost chatRoomInfoPost
){
}