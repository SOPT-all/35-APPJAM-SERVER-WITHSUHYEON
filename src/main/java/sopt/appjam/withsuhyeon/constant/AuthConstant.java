package sopt.appjam.withsuhyeon.constant;

public class AuthConstant {
    public static final String USER_ID_CLAIM_NAME = "uid";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String[] AUTH_WHITELIST = {
            "/api/vi/auth/signup",
            "/api/vi/auth/signin",
    };
    private AuthConstant() {
    }
}
