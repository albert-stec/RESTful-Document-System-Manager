package com.stecalbert.restfuldms.security;

final class SecurityConstants {
    public static final String SIGN_UP_URL = "/users";
    static final String SECRET = "SecretKeyToGenJWTs";
    static final long EXPIRATION_TIME = 864_000_000;
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";

    private SecurityConstants() {
    }
}
