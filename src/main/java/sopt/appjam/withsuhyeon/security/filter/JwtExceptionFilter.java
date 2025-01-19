package sopt.appjam.withsuhyeon.security.filter;

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
import sopt.appjam.withsuhyeon.global.exception.BaseException;
import sopt.appjam.withsuhyeon.global.exception.ErrorCode;
import sopt.appjam.withsuhyeon.global.exception.GlobalErrorCode;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NonNull final HttpServletRequest request,
            @NonNull final HttpServletResponse response,
            @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException e) {
            handleException(request, response, filterChain, GlobalErrorCode.TOKEN_MALFORMED_ERROR, e);
        } catch (IllegalArgumentException e) {
            handleException(request, response, filterChain, GlobalErrorCode.TOKEN_TYPE_ERROR, e);
        } catch (ExpiredJwtException e) {
            handleException(request, response, filterChain, GlobalErrorCode.EXPIRED_TOKEN_ERROR, e);
        } catch (UnsupportedJwtException e) {
            handleException(request, response, filterChain, GlobalErrorCode.TOKEN_UNSUPPORTED_ERROR, e);
        } catch (JwtException e) {
            handleException(request, response, filterChain, GlobalErrorCode.TOKEN_UNKNOWN_ERROR, e);
        } catch (BaseException e) {
            handleException(request, response, filterChain, e.getCode(), e);
        } catch (Exception e) {
            handleException(request, response, filterChain, GlobalErrorCode.INTERNAL_SERVER_ERROR, e);
        }
    }


    private void handleException(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain,
            final ErrorCode errorCode,
            final Exception e
    ) throws ServletException, IOException {
        log.error(e.getMessage(), e);
        request.setAttribute("exception", errorCode);
        filterChain.doFilter(request, response);
    }
}
