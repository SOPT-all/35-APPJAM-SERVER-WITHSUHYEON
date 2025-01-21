package sopt.appjam.withsuhyeon.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import sopt.appjam.withsuhyeon.global.exception.ErrorCode;
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum BlockErrorCode implements ErrorCode {
    BLOCK_NOT_FOUND(HttpStatus.NOT_FOUND, "BLOCK_001", "해당 유저 id의 차단 번호 목록에서 해당 번호를 찾을 수 없습니다."),
    BLOCK_SELF_CALL_BAD_REQUEST(HttpStatus.BAD_REQUEST, "BLOCK_002", "유저 본인의 전화번호는 차단할 수 없습니다."),
    BLOCK_FORMAT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "BLOCK_003", "해당 전화번호는 형식에 맞지 않습니다."),
    BLOCK_ALREADY_EXISTS_BAD_REQUEST(HttpStatus.BAD_REQUEST, "BLOCK_004", "이미 차단 등록된 전화번호입니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
