package com.converter.converter.auth.jwt;

import com.converter.converter.auth.entity.Roles;
import com.converter.converter.auth.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class JwtTokenProvider {
    private String secret = "SecretSecretSecretSecretSecretSecretSecretSecretSecretSecret";
    private final long timeValidity = new Date(System.currentTimeMillis() + 30 * 60 * 1000).getTime();
    private UserService service;

    @Autowired
    public JwtTokenProvider(UserService service) {
        this.service = service;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String login, List<Roles> roleList) {
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("roles", getRoleNames(roleList));
        claims.put("password", service.findUserByLogin(login).getPassword());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now.getTime() + timeValidity))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails users = service.loadUserByUsername(getLogin(token));
        return new UsernamePasswordAuthenticationToken(users, "", users.getAuthorities());
    }

    public String getLogin(String token) {
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes())).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        System.out.println("return null in method resolveToken from JwtTokenProvider");
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secret.getBytes()).build().parseClaimsJws(token);
            if (claims.getBody().getIssuedAt().before(new Date())) {
                return false;
            }
            return false;
        } catch (JwtException | IllegalArgumentException exception) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    private List<String> getRoleNames(List<Roles> rolesList) {
        List<String> result = new ArrayList<>();
        rolesList.forEach(roles -> result.add(roles.getRole()));
        return result;
    }
}
