package sopt.appjam.withsuhyeon.global.interceptor.post;

import lombok.NonNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import sopt.appjam.withsuhyeon.global.base.BaseResponse;
import sopt.appjam.withsuhyeon.global.exception.CustomErrorResponse;

@RestControllerAdvice
public class ResponseInterceptor implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(
            MethodParameter returnType,
            @NonNull Class converterType
    ) {
        return !(returnType.getParameterType() == BaseResponse.class)
                && MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        if(body instanceof CustomErrorResponse)
            return BaseResponse.fail((CustomErrorResponse)body);
        return BaseResponse.success(body);
    }
}
