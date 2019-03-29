package com.stecalbert.restfuldms.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stecalbert.restfuldms.model.entity.UserEntity;
import org.springframework.http.HttpHeaders;
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

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                            JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
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
        String token = jwtTokenProvider.buildJwtTokenAndGet(principal);
        response.addHeader(HttpHeaders.AUTHORIZATION,
                jwtTokenProvider.addTokenTypePrefix(token));
    }


}
