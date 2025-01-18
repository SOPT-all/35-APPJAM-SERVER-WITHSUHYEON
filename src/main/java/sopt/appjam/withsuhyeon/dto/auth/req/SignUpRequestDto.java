package sopt.appjam.withsuhyeon.dto.auth.req;

import sopt.appjam.withsuhyeon.constant.ProfileImage;
import sopt.appjam.withsuhyeon.constant.Region;

public record SignUpRequestDto(
        String phoneNumber,

        String nickname,

        Integer birthYear,

        Boolean gender,

        ProfileImage profileImage,

        Region region
) {
}

