package com.stecalbert.restfuldms.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpHeaders;
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


class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;

    JwtAuthorizationFilter(AuthenticationManager authManager, JwtTokenProvider jwtTokenProvider) {
        super(authManager);
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (jwtTokenProvider.isTokenPresentAndValidType(authorizationHeader)) {
            DecodedJWT decodedJwt = jwtTokenProvider.getDecodedToken(authorizationHeader);
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

    private static List<GrantedAuthority> mapToGrantedAuthorities(String[] authorities) {
        return !ArrayUtils.isEmpty(authorities)
                ? Arrays.stream(authorities)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }
}
