package sopt.appjam.withsuhyeon.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Request {
    PHOTO("사진 촬영"),
    CALL("전화 통화"),
    VIDEO("영상 통화");

    private final String value;

    public static List<String> getRequestOption() {
        return Arrays.stream(Request.values())  // 모든 enum 값을 리스트로 가져옴
                .map(Request::getValue)    // 각 enum의 value 매핑
                .collect(Collectors.toList());
    }
}
