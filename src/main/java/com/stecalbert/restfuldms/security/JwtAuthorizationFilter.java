package com.stecalbert.restfuldms.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.stecalbert.restfuldms.security.JwtConstants.AUTHORIZATION_HEADER_KEY;
import static com.stecalbert.restfuldms.security.JwtConstants.SECRET;
import static com.stecalbert.restfuldms.security.JwtConstants.TOKEN_PREFIX;

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
            DecodedJWT decodedJwt = getDecodedToken(authorizationHeader);
            String username = decodedJwt.getSubject();
            String[] authorities = decodedJwt.getClaim("authorities").asArray(String.class);

            var authenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    mapToGrantedAuthorities(authorities));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(req, res);
    }

    private boolean isPresentAndValid(String authorizationHeader) {
        return authorizationHeader != null
                && authorizationHeader.startsWith(TOKEN_PREFIX);
    }

    private DecodedJWT getDecodedToken(String token) {
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""));
    }

    private List<GrantedAuthority> mapToGrantedAuthorities(String[] authorities) {
        return !ArrayUtils.isEmpty(authorities)
                ? Arrays.stream(authorities)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }
}
