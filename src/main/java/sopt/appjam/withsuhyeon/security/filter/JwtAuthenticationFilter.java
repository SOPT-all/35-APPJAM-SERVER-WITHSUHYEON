package sopt.appjam.withsuhyeon.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sopt.appjam.withsuhyeon.constant.AuthConstant;
import sopt.appjam.withsuhyeon.security.info.UserAuthentication;
import sopt.appjam.withsuhyeon.util.JwtUtil;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        final String token = getJwtFromRequest(request);    //헤더에서 토큰 찾기
        if (StringUtils.hasText(token)) {   //토큰 있으면 토큰으로부터 유저 정보 가져오기
            log.info("token 이렇게 들어왔어요 : {}", token);
            Claims claims = jwtUtil.getTokenBody(token);
            log.info("claim 이렇게 들어왔어요 : {}", claims);
            Long userId = claims.get(AuthConstant.USER_ID_CLAIM_NAME, Long.class);
            UserAuthentication authentication = UserAuthentication.createUserAuthentication(userId); // 인증 객체 생성
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);    //다음 필터로 넘기기
    }

    private String getJwtFromRequest(final HttpServletRequest request) {
        String bearerToken = request.getHeader(AuthConstant.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AuthConstant.BEARER_PREFIX)) {
            return bearerToken.substring(AuthConstant.BEARER_PREFIX.length());
        }
        return null;
    }
}

