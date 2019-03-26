package com.stecalbert.restfuldms.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stecalbert.restfuldms.model.entity.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.stecalbert.restfuldms.security.JwtConstants.AUTHORIZATION_HEADER_KEY;
import static com.stecalbert.restfuldms.security.JwtConstants.EXPIRATION_TIME;
import static com.stecalbert.restfuldms.security.JwtConstants.SECRET;
import static com.stecalbert.restfuldms.security.JwtConstants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        UserEntity credentials = parseApplicationUserData(request);

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        Collections.emptyList())
        );
    }

    private UserEntity parseApplicationUserData(HttpServletRequest request) {
        try {
            return new ObjectMapper()
                    .readValue(request.getInputStream(), UserEntity.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error parsing application user data");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        User principal = (User) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject(principal.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));

        response.addHeader(AUTHORIZATION_HEADER_KEY, TOKEN_PREFIX + token);
    }
}
