package sopt.appjam.withsuhyeon.constant;

public enum Age {
    EARLY20("20 ~ 24"),
    LATE20("25 ~ 29"),
    EARLY30("30 ~ 34"),
    LATE30("35 ~ 39"),
    FORTY("40세 이상");

    private final String value;

    Age(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
