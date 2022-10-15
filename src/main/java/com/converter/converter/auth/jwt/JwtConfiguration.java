package com.converter.converter.auth.jwt;

import com.converter.converter.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {


    private final UserService service;
    private final JwtConfig config;
    private final JwtTools tools;

    @Autowired
    public JwtConfiguration(UserService service, JwtConfig config, JwtTools tools) {
        this.service = service;
        this.config = config;
        this.tools = tools;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        AuthenticationManager manager = builder.getSharedObject(AuthenticationManager.class);
        builder.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(manager, config, tools));
        builder.addFilterAfter(new JwtTokenFilter(tools), UsernamePasswordAuthenticationFilter.class);
    }
}
