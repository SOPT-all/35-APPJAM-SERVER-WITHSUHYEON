package sopt.appjam.withsuhyeon.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import sopt.appjam.withsuhyeon.global.exception.ErrorCode;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum GalleryErrorCode implements ErrorCode {
    GALLERY_TITLE_INVALID(HttpStatus.BAD_REQUEST, "GALLERY_001", "제목은 필수이며, 30자를 초과할 수 없습니다."),
    GALLERY_CONTENT_INVALID(HttpStatus.BAD_REQUEST, "GALLERY_002", "내용은 필수이며, 300자를 초과할 수 없습니다."),
    GALLERY_IMAGE_REQUIRED(HttpStatus.BAD_REQUEST, "GALLERY_003", "이미지는 필수 입력값입니다."),
    GALLERY_CATEGORY_INVALID(HttpStatus.BAD_REQUEST, "GALLERY_004", "유효하지 않은 카테고리를 입력하였습니다."),
    GALLERY_NOT_FOUND(HttpStatus.NOT_FOUND, "GALLERY_005", "요청한 갤러리를 찾을 수 없습니다."),
    GALLERY_USER_FORBIDDEN(HttpStatus.FORBIDDEN, "GALLERY_006", "해당 갤러리에 대한 권한이 없습니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}