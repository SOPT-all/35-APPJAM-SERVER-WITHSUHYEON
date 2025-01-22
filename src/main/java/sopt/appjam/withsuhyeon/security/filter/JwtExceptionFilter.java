package sopt.appjam.withsuhyeon.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import sopt.appjam.withsuhyeon.global.base.BaseResponse;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.global.exception.CustomErrorResponse;
import sopt.appjam.withsuhyeon.global.exception.ErrorCode;
import sopt.appjam.withsuhyeon.global.exception.GlobalErrorCode;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper; // 응답 직렬화용 Bean (주입받거나 직접 생성)

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException e) {
            handleException(response, GlobalErrorCode.TOKEN_MALFORMED_ERROR, e);
        } catch (IllegalArgumentException e) {
            handleException(response, GlobalErrorCode.TOKEN_TYPE_ERROR, e);
        } catch (ExpiredJwtException e) {
            handleException(response, GlobalErrorCode.EXPIRED_TOKEN_ERROR, e);
        } catch (UnsupportedJwtException e) {
            handleException(response, GlobalErrorCode.TOKEN_UNSUPPORTED_ERROR, e);
        } catch (JwtException e) {
            handleException(response, GlobalErrorCode.TOKEN_UNKNOWN_ERROR, e);
        } catch (BaseException e) {
            handleException(response, e.getCode(), e);
        } catch (Exception e) {
            handleException(response, GlobalErrorCode.INTERNAL_SERVER_ERROR, e);
        }
    }

    private void handleException(
            HttpServletResponse response,
            ErrorCode errorCode,
            Exception e
    ) throws IOException {
        log.error("[JwtExceptionFilter] 예외 발생 : {}", e.getMessage(), e);

        // HTTP Status 먼저 지정
        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        // CustomErrorResponse 생성
        CustomErrorResponse cer = CustomErrorResponse.from(errorCode);

        // BaseResponse.fail() 생성
        BaseResponse<?> baseResponse = BaseResponse.fail(cer);

        // JSON 직렬화 후 응답
        response.getWriter().write(
                objectMapper.writeValueAsString(baseResponse)
        );

        // 필터 체인 종료
        return;
    }

}
