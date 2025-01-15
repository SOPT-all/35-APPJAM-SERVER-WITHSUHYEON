package sopt.appjam.withsuhyeon.constant;

public enum Profile {
    IMAGE1("image1"),
    IMAGE2("image2"),
    IMAGE3("image3");

    private final String value;

    Profile(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
