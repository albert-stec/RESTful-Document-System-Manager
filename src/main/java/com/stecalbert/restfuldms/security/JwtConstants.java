package com.stecalbert.restfuldms.security;

final class JwtConstants {
    static final String SECRET = "SecretKeyToGenJWTs";
    static final long EXPIRATION_TIME = 864_000_000;
    static final String TOKEN_PREFIX = "Bearer ";
    static final String AUTHORIZATION_HEADER_KEY = "Authorization";

    private JwtConstants() {
    }
}
