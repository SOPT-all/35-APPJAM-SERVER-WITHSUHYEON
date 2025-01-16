package sopt.appjam.withsuhyeon.dto.auth.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import sopt.appjam.withsuhyeon.constant.ProfileImage;
import sopt.appjam.withsuhyeon.constant.Region;

public record SignupRequestDto(
        String phoneNumber,

        String nickname,

        Integer birthYear,

        Boolean gender,

        ProfileImage profileImage,

        Region region
) {
}

