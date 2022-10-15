package com.converter.converter.auth.configuration;

import com.converter.converter.auth.jwt.JwtConfig;
import com.converter.converter.auth.jwt.JwtConfiguration;
import com.converter.converter.auth.jwt.JwtTools;
import com.converter.converter.auth.service.OAuth2UserServiceImpl;
import com.converter.converter.auth.service.UserService;
import com.converter.converter.auth.tools.OAuth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("com.converter.converter.auth")
public class SecurityConfig {
    private final MyBasicAuthEntityPoint myBasicAuthEntryPoint;
    private final OAuth2UserServiceImpl OAuth2UserServiceImpl;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final UserService service;
    private final JwtConfig config;
    private final JwtTools tools;

    @Autowired
    public SecurityConfig(MyBasicAuthEntityPoint myBasicAuthEntryPoint,
                          OAuth2UserServiceImpl OAuth2UserServiceImpl,
                          OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler,
                          UserService service,
                          JwtConfig config,
                          JwtTools tools) {
        this.myBasicAuthEntryPoint = myBasicAuthEntryPoint;
        this.OAuth2UserServiceImpl = OAuth2UserServiceImpl;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
        this.service = service;
        this.config = config;
        this.tools = tools;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {

        https
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/user/login").permitAll()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/hello").hasAnyRole("USER", "ADMIN")
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
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(new JwtConfiguration(service, config, tools))
                .and()
                .httpBasic().authenticationEntryPoint(myBasicAuthEntryPoint)
                .and()
                .formLogin().loginPage("/login")
                .defaultSuccessUrl("/hello")
                .failureForwardUrl("/login").permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint().userService(OAuth2UserServiceImpl)
                .and()
                .failureUrl("/login").permitAll()
                .successHandler(oAuth2LoginSuccessHandler)
                .and()
                .logout().logoutUrl("/logout").permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login");
        return https.build();
    }
}