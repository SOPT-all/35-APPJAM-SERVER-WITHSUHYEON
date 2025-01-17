package sopt.appjam.withsuhyeon.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.global.exception.GlobalErrorCode;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {  //인증 실패시 처리
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 인증 실패를 처리하고 적절한 JSON 응답을 반환합니다.
     * 이 메서드는 인증되지 않은 사용자가 보호된 리소스에 접근하거나,
     * 요청 처리 중 인증에 실패했을 때 호출됩니다.
     *
     * <p>이 메서드는 {@code HttpServletRequest} 객체에서
     * {@code exception} 속성에 저장된 커스텀 에러 코드를 가져옵니다.
     * 만약 해당 속성이 존재하지 않는 경우 기본값으로 {@code GlobalErrorCode.NOT_FOUND}를 설정합니다.
     * 이후 에러 상세 정보를 포함한 JSON 응답을 생성하고,
     * HTTP 상태 코드와 함께 클라이언트에게 반환합니다.</p>
     *
     * @param request  인증 실패가 발생한 {@code HttpServletRequest} 객체
     * @param response 클라이언트에게 에러 응답을 전송할 {@code HttpServletResponse} 객체
     * @param authException 인증 실패를 유발한 {@code AuthenticationException} 예외 객체
     * @throws IOException 응답 생성 및 전송 중 입출력 예외가 발생한 경우
     */
    @Override
    public void commence(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException authException
    ) throws IOException {
        GlobalErrorCode errorCode = (GlobalErrorCode) request.getAttribute("exception");
        if (errorCode == null) {
            errorCode = GlobalErrorCode.NOT_FOUND;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(errorCode.getStatus().value());
        response.getWriter().write(
                objectMapper.writeValueAsString(BaseException.type(GlobalErrorCode.UNAUTHORIZED))
        );
    }

}