package sopt.appjam.withsuhyeon.dto.gallery.res;

import lombok.Builder;
import sopt.appjam.withsuhyeon.dto.constant.res.Categories;

import java.util.List;

@Builder
public record GalleriesListResponse(
    List<Categories> categories,
    List<GalleryResponse> galleries
) {
}
