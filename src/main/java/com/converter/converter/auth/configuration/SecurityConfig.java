package com.converter.converter.auth.configuration;

import com.converter.converter.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("com.converter.converter.auth")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    private MyBasicAuthenticationEntryPoint myBasicAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .mvcMatchers("/home").permitAll()
                .mvcMatchers("/hello").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .mvcMatchers("/history").hasAnyAuthority("ROLE_USER")
                .mvcMatchers("/converter").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/hello", true)
                .failureForwardUrl("/login").permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .defaultSuccessUrl("/hello", true)
                .failureUrl("/login").permitAll()
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/login");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}