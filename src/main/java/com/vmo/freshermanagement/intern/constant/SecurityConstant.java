package com.vmo.freshermanagement.intern.constant;

public class SecurityConstant {
    public static final long EXPIRATION_JWT_TIME = 432_000_000; // 5 days expressed in millisecond
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot verified";
    public static final String VMO_INTERN = "VMO Intern T9 2023";
    public static final String EMAIL_TO_SEND = "conglvfx20485@funix.edu.vn";
    public static final String ADMINISTRATION = "Admin";
    public static final String AUTHORITY = "Authority";
    public static final String FORBIDDEN_MESSAGE = "You need to login to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/", "/v1/login"};
    public static final String[] AUTH_WHITELIST = { "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
            "/configuration/security", "/swagger-ui.html", "/webjars/**", "/v3/api-docs/**", "/api/public/**",
            "/api/public/authenticate", "/actuator/*", "/swagger-ui/**" };
}
