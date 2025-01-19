package sopt.appjam.withsuhyeon.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum GlobalErrorCode implements ErrorCode{
    /**
     * 200 : 요청 성공
     */
    SUCCESS(HttpStatus.OK, "SUCCESS", "요청에 성공했습니다."),
    CREATED(HttpStatus.CREATED, "CREATED", "요청에 성공했으며 리소스가 정상적으로 생성되었습니다."),
    ACCEPTED(HttpStatus.ACCEPTED, "ACCEPTED", "요청에 성공했으나 처리가 완료되지 않았습니다."),

    /**
     * 400 : 요청 실패
     */
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "REQUEST_001", "잘못된 요청입니다."),
    EMPTY_JWT(HttpStatus.UNAUTHORIZED, "REQUEST_002", "JWT를 입력해주세요."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "REQUEST_003", "유효하지 않은 JWT입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "REQUEST_004", "JWT 토큰이 일치하지 않습니다"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "REQUEST_005", "자격 증명이 이루어지지 않았습니다."),
    INVALID_USER(HttpStatus.FORBIDDEN,"REQUEST_006","권한이 없는 유저의 접근입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "REQUEST_007", "잘못된 접근입니다."),
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "REQUEST_008", "만료된 접근입니다."),
    NOT_SUPPORTED_URI_ERROR(HttpStatus.NOT_FOUND, "REQUEST_009", "지원하지 않는 URL입니다."),
    NOT_SUPPORTED_METHOD_ERROR(HttpStatus.METHOD_NOT_ALLOWED, "REQUEST_010", "지원하지 않는 HTTP Method 요청입니다."),
    NOT_SUPPORTED_MEDIA_TYPE_ERROR(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "REQUEST_011", "잘못된 미디어 타입입니다."),
    INVALID_ENUM(HttpStatus.BAD_REQUEST, "REQUEST_012", "Enum 타입으로 변경할 수 없습니다."),
    INVALID_HEADER_VALUE(HttpStatus.UNAUTHORIZED, "REQUEST_013", "올바르지 않은 헤더값입니다."),
    EXPIRED_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "REQUEST_014", "만료된 토큰입니다."),
    TOKEN_MALFORMED_ERROR(HttpStatus.UNAUTHORIZED, "REQUEST_015", "토큰이 올바르지 않습니다."),
    TOKEN_TYPE_ERROR(HttpStatus.UNAUTHORIZED, "REQUEST_016", "토큰 타입이 일치하지 않거나 비어있습니다."),
    TOKEN_UNSUPPORTED_ERROR(HttpStatus.UNAUTHORIZED, "REQUEST_017", "지원하지않는 토큰입니다."),
    TOKEN_UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED, "REQUEST_018", "알 수 없는 토큰입니다."),


    /**
     * 500 : 응답 실패
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "RESPONSE_001", "서버와의 연결에 실패했습니다."),
    BAD_GATEWAY(HttpStatus.BAD_GATEWAY, "RESPONSE_002", "다른 서버로부터 잘못된 응답이 수신되었습니다."),
    INSUFFICIENT_STORAGE(HttpStatus.INSUFFICIENT_STORAGE, "RESPONSE_003", "서버의 용량이 부족해 요청에 실패했습니다."),
    UNSUPPORTED_ENCODING(HttpStatus.INTERNAL_SERVER_ERROR, "RESPONSE_004", "지원하지 않는 인코딩입니다."),
    CANNOT_CONVERT_IMAGE(HttpStatus.INTERNAL_SERVER_ERROR, "RESPONSE_005", "이미지 변환에 실패했습니다."),
    FIREBASE_JSON_EMPTY(HttpStatus.INTERNAL_SERVER_ERROR, "RESPONSE_006", "FCM JSON 파일을 읽지 못하였습니다."),
    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
