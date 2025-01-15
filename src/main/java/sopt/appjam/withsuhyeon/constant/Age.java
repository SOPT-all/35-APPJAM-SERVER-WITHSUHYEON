package sopt.appjam.withsuhyeon.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Age {
    EARLY20("20 ~ 24"),
    LATE20("25 ~ 29"),
    EARLY30("30 ~ 34"),
    LATE30("35 ~ 39"),
    FORTY("40세 이상");

    private final String value;
}
