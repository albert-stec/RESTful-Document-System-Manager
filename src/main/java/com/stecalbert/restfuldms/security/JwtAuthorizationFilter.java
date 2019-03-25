package com.stecalbert.restfuldms.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static com.stecalbert.restfuldms.security.SecurityConstants.AUTHORIZATION_HEADER_KEY;
import static com.stecalbert.restfuldms.security.SecurityConstants.SECRET;
import static com.stecalbert.restfuldms.security.SecurityConstants.TOKEN_PREFIX;

class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    JwtAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = req.getHeader(AUTHORIZATION_HEADER_KEY);
        if (isPresentAndValid(authorizationHeader)) {
            Optional<String> userOptional = getUser(authorizationHeader);
            userOptional.ifPresent(e -> {
                var authenticationToken = new UsernamePasswordAuthenticationToken(userOptional.get(),
                        null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            });
        }

        chain.doFilter(req, res);
    }

    private boolean isPresentAndValid(String authorizationHeader) {
        return authorizationHeader != null
                && authorizationHeader.startsWith(TOKEN_PREFIX);
    }

    private Optional<String> getUser(String token) {
        return Optional.ofNullable(JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject());
    }
}
