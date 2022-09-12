package com.converter.converter.auth.jwt;

import com.converter.converter.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    //    private JwtTokenProvider provider;
    private UserService service;

    @Autowired
    public JwtConfiguration(UserService service) {
        this.service = service;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
//        JwtTokenFilter filter = new JwtTokenFilter(provider);
        AuthenticationManager manager = builder.getSharedObject(AuthenticationManager.class);
        builder.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(manager, this.service));
//        builder.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
