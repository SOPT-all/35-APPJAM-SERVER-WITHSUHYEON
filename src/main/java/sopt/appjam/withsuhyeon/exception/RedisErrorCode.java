package sopt.appjam.withsuhyeon.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import sopt.appjam.withsuhyeon.global.exception.ErrorCode;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RedisErrorCode implements ErrorCode {
    INVALID_CERTIFICATION_NUMBER(HttpStatus.BAD_REQUEST, "REDIS_001", "유효하지 않은 인증번호입니다."),
    EXPIRED_CERTIFICATION_NUMBER(HttpStatus.BAD_REQUEST, "REDIS_002", "인증번호가 만료되었습니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
