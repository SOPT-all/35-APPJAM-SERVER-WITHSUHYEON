package sopt.appjam.withsuhyeon.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ProfileImage {
    IMAGE1("image1"),
    IMAGE2("image2"),
    IMAGE3("image3");

    private final String value;
}
