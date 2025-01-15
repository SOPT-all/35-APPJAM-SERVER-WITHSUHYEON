package sopt.appjam.withsuhyeon.global.base;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import sopt.appjam.withsuhyeon.global.exception.CustomErrorResponse;
import sopt.appjam.withsuhyeon.global.exception.GlobalErrorCode;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"errorCode", "message", "result"})
public class BaseResponse<T> {
    private final String errorCode;
    private final String message;
    private T result;

    public static <T> BaseResponse<T> success(final T data) {
        return new BaseResponse<>(null, "SUCCESS", data);
    }

    public static <T> BaseResponse<T> fail(CustomErrorResponse customErrorResponse) {
        return new BaseResponse<>(customErrorResponse.getErrorCode(), customErrorResponse.getMessage(), null);
    }

    public BaseResponse(T result) {
        this.errorCode = GlobalErrorCode.SUCCESS.getErrorCode();
        this.message = GlobalErrorCode.SUCCESS.getMessage();
        this.result = result;
    }
}
