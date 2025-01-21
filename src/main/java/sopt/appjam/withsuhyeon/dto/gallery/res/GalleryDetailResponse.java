package sopt.appjam.withsuhyeon.dto.gallery.res;

import lombok.Builder;

@Builder
public record GalleryDetailResponse(
        String imageUrl,
        String category,
        String title,
        String profileImage,
        String nickname,
        String createdAt,
        String content,
        Boolean owner
) {
}
