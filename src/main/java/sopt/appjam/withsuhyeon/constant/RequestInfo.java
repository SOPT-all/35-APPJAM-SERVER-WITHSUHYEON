package sopt.appjam.withsuhyeon.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import sopt.appjam.withsuhyeon.exception.PostErrorCode;
import sopt.appjam.withsuhyeon.global.exception.BaseException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RequestInfo {
    PHOTO("PHOTO", "사진 촬영"),
    CALL("CALL", "전화 통화"),
    VIDEO("VIDEO", "영상 통화");

    private final String key;
    private final String value;

    public static List<String> getRequestOption() {
        return Arrays.stream(RequestInfo.values())  // 모든 enum 값을 리스트로 가져옴
                .map(RequestInfo::getValue)    // 각 enum의 value 매핑
                .collect(Collectors.toList());
    }

    public static RequestInfo fromValue(String value) {

        for (RequestInfo requestInfo : RequestInfo.values()) {
            if (requestInfo.getValue().equals(value)) {
                return requestInfo;
            }
        }
        throw BaseException.type(PostErrorCode.POST_REQUEST_INVALID_INPUT);
    }
}