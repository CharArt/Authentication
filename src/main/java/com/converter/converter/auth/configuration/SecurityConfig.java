package com.converter.converter.auth.configuration;

import com.converter.converter.auth.repository.dto.CustomOAuth2User;
import com.converter.converter.auth.service.CustomOAuth2UserService;
import com.converter.converter.auth.service.GoogleUsersService;
import com.converter.converter.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("com.converter.converter.auth")
public class SecurityConfig {
    private final MyBasicAuthEntityPoint myBasicAuthEntryPoint;
    private final UserService userService;
    private final GoogleUsersService googleService;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    public SecurityConfig(MyBasicAuthEntityPoint myBasicAuthEntryPoint,
                          UserService userService,
                          GoogleUsersService googleService,
                          CustomOAuth2UserService customOAuth2UserService) {
        this.myBasicAuthEntryPoint = myBasicAuthEntryPoint;
        this.userService = userService;
        this.googleService = googleService;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
        https
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/hello").hasAnyRole("USER", "ADMIN")
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers(HttpMethod.GET, "/api/user/").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/user/{id}").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/user/NSP").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/user/Email").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/user/Gender").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/user/Phone").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/user/Age").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/user/Enable").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/user/Save").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/user/Update").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/user/Delete").hasAnyRole("ADMIN")
                .and()
                .httpBasic().authenticationEntryPoint(myBasicAuthEntryPoint)
                .and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/hello", true)
                .failureForwardUrl("/login").permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .failureUrl("/login").permitAll()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
                        response.sendRedirect("/hello");
                    }
                })
                .and()
                .logout().permitAll()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login");
        return https.build();
    }
}