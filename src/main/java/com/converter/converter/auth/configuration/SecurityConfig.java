package com.converter.converter.auth.configuration;

import com.converter.converter.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("com.converter.converter.auth")
public class SecurityConfig {

    private final UserService userService;
    private final MyBasicAuthEntityPoint myBasicAuthEntryPoint;

    @Autowired
    public SecurityConfig(UserService userService, MyBasicAuthEntityPoint myBasicAuthEntityPoint) {
        this.userService = userService;
        this.myBasicAuthEntryPoint = myBasicAuthEntityPoint;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
        https
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .mvcMatchers("/home").permitAll()
                .mvcMatchers("/api/user/").hasAnyAuthority("ROLE_USER")
                .mvcMatchers("/api/user/NSP").hasAnyAuthority("ROLE_USER")
                .mvcMatchers("/api/user/Email").hasAnyAuthority("ROLE_USER")
                .mvcMatchers("/api/user/Gender").hasAnyAuthority("ROLE_USER")
                .mvcMatchers("/api/user/Phone").hasAnyAuthority("ROLE_USER")
                .mvcMatchers("/api/user/Age").hasAnyAuthority("ROLE_USER")
                .mvcMatchers("/api/user/Enable").hasAnyAuthority("ROLE_USER")
                .mvcMatchers("/api/user/Save").hasAnyAuthority("ROLE_USER")
                .mvcMatchers("/api/user/Update").hasAnyAuthority("ROLE_USER")
                .mvcMatchers("/api/user/Delete").hasAnyAuthority("ROLE_USER")
                .mvcMatchers("/hello").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .mvcMatchers("/history").hasAnyAuthority("ROLE_USER")
                .mvcMatchers("/converter").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                .anyRequest().authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(myBasicAuthEntryPoint)
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

        return https.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}