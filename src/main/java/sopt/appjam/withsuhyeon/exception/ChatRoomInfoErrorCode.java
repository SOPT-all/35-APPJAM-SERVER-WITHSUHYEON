package sopt.appjam.withsuhyeon.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import sopt.appjam.withsuhyeon.global.exception.ErrorCode;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ChatRoomInfoErrorCode implements ErrorCode {
    CHAT_ROOM_INFO_NOT_FOUND(HttpStatus.NOT_FOUND, "CHAT_ROOM_INFO_001", "해당 roomNumber 에 해당하는 채팅방 인포를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;
}
