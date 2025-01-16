package sopt.appjam.withsuhyeon.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
