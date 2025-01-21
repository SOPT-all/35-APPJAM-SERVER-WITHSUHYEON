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
    IMAGE1("IMAGE_1", "image1"),
    IMAGE2("IMAGE_2", "image2"),
    IMAGE3("IMAGE_3", "image3");

    private final String key;
    private final String value;

    public static ProfileImage from(String value) {
        for (ProfileImage profileImage : ProfileImage.values()) {
            if (profileImage.getValue().equals(value)) {
                return profileImage;
            }
        }
        throw BaseException.type(ConstantErrorCode.NOT_FOUND_CATEGORY);
    }
}