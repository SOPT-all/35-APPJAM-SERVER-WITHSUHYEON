package sopt.appjam.withsuhyeon.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProfileImage {
    IMAGE1("IMAGE_1", "image1"),
    IMAGE2("IMAGE_2", "image2"),
    IMAGE3("IMAGE_3", "image3");

    private final String key;
    private final String value;
}