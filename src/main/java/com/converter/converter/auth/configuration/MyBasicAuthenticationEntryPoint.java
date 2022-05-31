package com.converter.converter.auth.configuration;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    @Override
    public void afterPropertiesSet() {
        setRealmName("Charartpav");
        super.afterPropertiesSet();
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName() + "");
        response.addHeader("Content-Type", "application/json" + getRealmName() + "");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + authException.getMessage());
    }
}
