package sopt.appjam.withsuhyeon.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sopt.appjam.withsuhyeon.constant.AuthConstant;
import sopt.appjam.withsuhyeon.security.filter.JwtAuthenticationFilter;
import sopt.appjam.withsuhyeon.security.filter.JwtExceptionFilter;
import sopt.appjam.withsuhyeon.security.handler.JwtAuthenticationEntryPoint;
import sopt.appjam.withsuhyeon.util.JwtUtil;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final ObjectMapper objectMapper;  // Spring Boot에서 기본으로 빈 등록된 ObjectMapper를 주입받을 수 있음

    /**
     * ExceptionFilter Bean
     */
    @Bean
    public JwtExceptionFilter jwtExceptionFilter() {
        // 생성자에 objectMapper를 넘겨줌
        return new JwtExceptionFilter(objectMapper);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AuthConstant.AUTH_WHITELIST).permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                // 여기서 Bean으로 등록된 jwtExceptionFilter()를 호출
                .addFilterBefore(jwtExceptionFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
