package com.stecalbert.restfuldms.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
class JwtTokenProvider {

    @Value("${client.jwt.encryption_key}")
    private String secretKey;

    @Value("${client.jwt.token_type}")
    private String tokenType;

    @Value("${client.jwt.expiration_time}")
    private Long expirationTime;

    String buildJwtTokenAndGet(User principal) {
        String[] authorities = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        return JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .withArrayClaim("authorities", authorities)
                .sign(HMAC512(secretKey.getBytes()));
    }

    DecodedJWT getDecodedToken(String token) {
        return JWT.require(Algorithm.HMAC512(secretKey.getBytes()))
                .build()
                .verify(token.replace(tokenType + StringUtils.SPACE, ""));
    }

    boolean isTokenPresentAndValidType(String authorizationHeader) {
        return authorizationHeader != null
                && authorizationHeader.startsWith(tokenType);
    }

    String addTokenTypePrefix(String token) {
        return tokenType + StringUtils.SPACE + token;
    }
}
