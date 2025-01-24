package sopt.appjam.withsuhyeon.dto.post.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record PostRequestDto(
        Boolean gender,

        @NotBlank(message = "게시글 작성 시 성별을 필수로 선택해야 합니다.")
        String age,

        List<String> requests,

        @NotBlank(message = "게시글 작성 시 관심지역은 필수로 입력해야 합니다.")
        String region,

        @NotBlank(message = "게시글 작성 시 날짜 및 시간은 필수로 입력해야 합니다.")
        LocalDateTime date,

        @PositiveOrZero
        @Max(99999)
        Integer price,

        @NotBlank(message = "게시글 작성 시 제목은 필수 입력값입니다.")
        @Size(max = 30, message = "게시글 작성 시 제목은 최대 30자까지 입력 가능합니다.")
        String title,

        @NotBlank(message = "게시글 작성 시 내용은 필수 입력값입니다.")
        @Size(max = 300, message = "게시글 작성 시 내용은 최대 300자까지 입력 가능합니다.")
        String content
) {
}

