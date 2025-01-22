package sopt.appjam.withsuhyeon.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import sopt.appjam.withsuhyeon.global.exception.ErrorCode;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum UserErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_001", "해당 ID에 해당하는 유저를 찾을 수 없습니다."),
    USER_NOT_REGISTERED(HttpStatus.BAD_REQUEST, "USER_002", "해당 휴대폰 번호로 회원가입한 유저가 없습니다."),
    USER_ALREADY_REGISTERED(HttpStatus.CONFLICT, "USER_003", "이미 회원가입한 휴대폰 번호입니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
