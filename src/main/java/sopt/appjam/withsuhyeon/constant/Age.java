package sopt.appjam.withsuhyeon.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import sopt.appjam.withsuhyeon.exception.PostErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Age {
    EARLY20("EARLY_20", "20 ~ 24"),
    LATE20("LATE_20", "25 ~ 29"),
    EARLY30("EARLY_30", "30 ~ 34"),
    LATE30("LATE_30", "35 ~ 39"),
    FORTY("FORTY_PLUS", "40세 이상");

    private final String key;
    private final String value;

    public static Age fromValue(String value) {
        for (Age age : Age.values()) {
            if (age.getValue().equals(value)) {
                return age;
            }
        }
        throw BaseException.type(PostErrorCode.POST_AGE_INVALID_INPUT);
    }
}
