package sopt.appjam.withsuhyeon.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import sopt.appjam.withsuhyeon.global.exception.ErrorCode;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ConstantErrorCode implements ErrorCode {
    NOT_FOUND_CATEGORY(HttpStatus.BAD_REQUEST, "CONSTANT_001","유효하지 않은 카테고리 입니다."),
    NOT_FOUND_PROFILE_IMAGE(HttpStatus.BAD_REQUEST, "CONSTANT_002","유효하지 않은 프로필 이미지 입니다."),
    NOT_FOUND_LOCATION_FROM_SUBLOCATION(HttpStatus.BAD_REQUEST, "CONSTANT_003","해당 하위지역에 해당하는 상위지역을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
