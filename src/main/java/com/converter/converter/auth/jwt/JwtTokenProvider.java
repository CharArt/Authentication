package com.converter.converter.auth.jwt;

import com.converter.converter.auth.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private UserService service;

    private String secret = "SecretSecretSecretSecretSecretSecretSecretSecret";

    @Autowired
    public JwtTokenProvider(UserService service) {
        this.service = service;
    }

    public void createToken() {

        String token = Jwts.builder()
                .setIssuedAt(new Date())
                .claim("User",service.findUserById(1L))
                .claim("authorities", service.findUserById(1L).getAuthorities())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
        System.out.println(token);
    }
}
