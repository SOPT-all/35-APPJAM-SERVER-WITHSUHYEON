package sopt.appjam.withsuhyeon.dto.auth.req;

import jakarta.validation.constraints.Pattern;
import sopt.appjam.withsuhyeon.constant.ProfileImage;
import sopt.appjam.withsuhyeon.constant.Region;

public record SignUpRequestDto(
        @Pattern(regexp = "^01([0|1|6|7|8|9])\\d{4}\\d{4}", message = "회원가입할 번호는 전화번호 형식에 맞게 11자로 입력해야 합니다.")

        String phoneNumber,

        String nickname,

        Integer birthYear,

        Boolean gender,

        String profileImage,

        String region
) {
}

