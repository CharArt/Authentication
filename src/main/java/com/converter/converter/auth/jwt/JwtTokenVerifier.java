package com.converter.converter.auth.jwt;

import com.converter.converter.auth.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenVerifier extends OncePerRequestFilter {
    private final UserService service;
    private final JwtConfig config;
    private final JwtSecretKey secretKey;

    @Autowired
    public JwtTokenVerifier(UserService service, JwtConfig config, JwtSecretKey secretKey) {
        this.service = service;
        this.config = config;
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(config.getAuthorizationHeader());

        if ((authorizationHeader.isEmpty() || authorizationHeader == null) || !authorizationHeader.startsWith(config.getTokenPrefix())) {
            filterChain.doFilter(request, response);
        }
        String token = authorizationHeader.replace(config.getTokenPrefix(), "");
        try {

            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey.secretKey())
                    .build().parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            String username = body.getSubject();
            UserDetails user = service.loadUserByUsername(username);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username, null, user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (JwtException e) {
            throw new IllegalStateException(String.format("Token %s can not be trust", token));
        }
        filterChain.doFilter(request, response);
    }
}
