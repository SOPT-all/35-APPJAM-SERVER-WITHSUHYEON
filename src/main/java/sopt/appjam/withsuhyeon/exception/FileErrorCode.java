package sopt.appjam.withsuhyeon.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import sopt.appjam.withsuhyeon.global.exception.ErrorCode;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum FileErrorCode implements ErrorCode {
    FILE_CONVERT_ERROR(HttpStatus.BAD_REQUEST, "FILE_001", "파일 전환 중 에러가 발생했습니다."),
    S3_ERROR(HttpStatus.BAD_REQUEST, "FILE_002", "S3 에러 발생");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
