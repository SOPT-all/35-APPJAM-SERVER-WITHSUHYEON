package sopt.appjam.withsuhyeon.constant;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public enum Request {
    PHOTO("photo", "사진 촬영"),
    CALL("call", "전화 통화"),
    VIDEO("video", "영상 통화");

    private final String key;
    private final String value;

    Request(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static List<String> getRequestOption() {
        return Arrays.stream(Request.values())  // 모든 enum 값을 리스트로 가져옴
                .map(Request::getValue)    // 각 enum의 value 매핑
                .collect(Collectors.toList());
    }

}
