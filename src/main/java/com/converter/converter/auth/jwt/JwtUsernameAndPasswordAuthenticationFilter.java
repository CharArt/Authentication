package com.converter.converter.auth.jwt;

import com.converter.converter.auth.repository.dto.AuthenticationUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig config;
    private final JwtSecretKey secretKey;

    @Autowired
    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                      JwtConfig config,
                                                      JwtSecretKey secretKey) {
        this.authenticationManager = authenticationManager;
        this.config = config;
        this.secretKey = secretKey;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationUserDTO userDTO = new ObjectMapper()
                    .readValue(request.getInputStream(), AuthenticationUserDTO.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDTO.getUsername(), userDTO.getPassword());
            return this.authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        long timeValidity = new Date(System.currentTimeMillis() + 30 * 60 * 1000).getTime();
        Date now = new Date();

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .setIssuedAt(new Date(now.getTime() + timeValidity))
                .signWith(secretKey.secretKey())
                .compact();
        response.addHeader(config.getAuthorizationHeader(), config.getTokenPrefix() + token);
    }
}
