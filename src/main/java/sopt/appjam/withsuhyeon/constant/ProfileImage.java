package sopt.appjam.withsuhyeon.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import sopt.appjam.withsuhyeon.exception.ConstantErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;

@Getter
@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProfileImage {
    PURPLE_SUMA("PURPLE_SUMA", "img_purple_suma"),
    RED_SUMA("RED_SUMA", "img_red_suma"),
    GREEN_SUMA("GREEN_SUMA", "img_green_suma"),
    BLUE_SUMA("BLUE_SUMA", "img_blue_suma");

    private final String key;
    private final String value;

    public static ProfileImage from(String value) {
        for (ProfileImage profileImage : ProfileImage.values()) {
            if (profileImage.getValue().equals(value)) {
                return profileImage;
            }
        }
        throw BaseException.type(ConstantErrorCode.NOT_FOUND_PROFILE_IMAGE);
    }
}