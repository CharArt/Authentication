package com.converter.converter.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "application.jwt")
@Component
public class JwtConfig {
    private String secretKey;
    private String tokenPrefix;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public String getAccessTokenHeader() {
        return "Access_token";
    }

    public String getRefreshTokenHeader() {
        return "Refresh_token";
    }
    public String getAuthorizationHeader() {
        return "Authorization";
    }
}
