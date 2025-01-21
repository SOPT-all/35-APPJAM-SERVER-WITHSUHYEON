package sopt.appjam.withsuhyeon.dto.gallery.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record   CreateGalleryRequest(
        @NotBlank(message = "카테고리는 필수 입력값입니다.")
        String category,

        @NotBlank(message = "제목은 필수 입력값입니다.")
        @Size(max = 30, message = "제목은 최대 30자까지 입력 가능합니다.")
        String title,

        @NotBlank(message = "내용은 필수 입력값입니다.")
        @Size(max = 300, message = "내용은 최대 300자까지 입력 가능합니다.")
        String content
) {
}