package com.converter.converter.auth.jwt;

import com.converter.converter.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

public class JwtConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    //    private JwtTokenProvider provider;
    private final UserService service;
    private final JwtConfig config;
    private final JwtSecretKey secretKey;

    @Autowired
    public JwtConfiguration(UserService service, JwtConfig config, JwtSecretKey secretKey) {
        this.service = service;
        this.config = config;
        this.secretKey = secretKey;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
//        JwtTokenFilter filter = new JwtTokenFilter(provider);
        AuthenticationManager manager = builder.getSharedObject(AuthenticationManager.class);
        builder.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(manager, config, secretKey));
        builder.addFilterAfter(new JwtTokenVerifier(service, config, secretKey), JwtUsernameAndPasswordAuthenticationFilter.class);
//        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
