package sopt.appjam.withsuhyeon.constant;

public class AuthConstant {
    public static final String USER_ID_CLAIM_NAME = "uid";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String[] AUTH_WHITELIST = {
            "/api/v1/auth/signup",
            "/api/v1/auth/signin",
            "/chat/**",
            "/api/v1/enums/regions",
            "/api/v1/message/send",
            "/api/v1/message/verify",
            "/api/v1/enums/categories" // WebSocket 경로 추가
    };
    private AuthConstant() {
    }
}
