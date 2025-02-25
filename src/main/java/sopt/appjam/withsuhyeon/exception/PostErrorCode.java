package sopt.appjam.withsuhyeon.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import sopt.appjam.withsuhyeon.global.exception.ErrorCode;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PostErrorCode implements ErrorCode {
    POST_PRICE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "POST_001", "사례금은 0 ~ 99999원 범위 내에서 입력 가능합니다."),
    POST_AGE_INVALID_INPUT(HttpStatus.BAD_REQUEST, "POST_002", "유효하지 않는 나이대를 입력하였습니다."),
    POST_REGION_INVALID_INPUT(HttpStatus.BAD_REQUEST, "POST_003", "유효하지 않은 관심 지역을 입력하였습니다."),
    POST_REQUEST_INVALID_INPUT(HttpStatus.BAD_REQUEST, "POST_004", "유효하지 않은 요청 사항을 입력하였습니다."),
    POST_USER_FORBIDDEN(HttpStatus.FORBIDDEN, "POST_005", "유저가 작성한 게시글이 아닙니다. 본인이 작성한 게시글만 삭제 가능합니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_006", "해당 id에 해당하는 게시글이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
