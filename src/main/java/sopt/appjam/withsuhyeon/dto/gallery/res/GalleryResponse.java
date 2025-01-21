package sopt.appjam.withsuhyeon.dto.gallery.res;

import lombok.Builder;

@Builder
public record GalleryResponse(
    Long galleryId,
    String imageUrl,
    String title
) {
}
