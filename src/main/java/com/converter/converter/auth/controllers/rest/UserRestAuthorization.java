package com.converter.converter.auth.controllers.rest;

import com.converter.converter.auth.entity.Users;
import com.converter.converter.auth.entity.repository.dto.AuthenticationUserDTO;
import com.converter.converter.auth.jwt.JwtConfig;
import com.converter.converter.auth.jwt.JwtTools;
import com.converter.converter.auth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/user/auth")
public class UserRestAuthorization {
    private final UserService service;
    private final JwtTools tools;
    private final JwtConfig config;

    public UserRestAuthorization(UserService service, JwtTools tools, JwtConfig config) {
        this.service = service;
        this.tools = tools;
        this.config = config;
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(HttpServletRequest request, HttpServletResponse response) {

        String authorizationToken = request.getHeader(config.getRefreshTokenHeader());
        String token = authorizationToken.replace(config.getTokenPrefix(), "");
        if (tools.validityRefreshTokenTime(token)) {
            Users users = service.findUserByLogin(tools.getLoginFormToken(token));
            response.addHeader(config.getAccessTokenHeader(), config.getTokenPrefix() + tools.getAccessToken(users));
            response.addHeader(config.getRefreshTokenHeader(), config.getTokenPrefix() + tools.getRefreshToken(users));
            return new ResponseEntity<String>("200", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Yours token rotten", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginA(HttpServletRequest request, HttpServletResponse response) {
        try {
            AuthenticationUserDTO userDTO = new ObjectMapper().readValue(request.getInputStream(), AuthenticationUserDTO.class);
        }
        catch ( IOException e){
           throw new RuntimeException(e);
        }
        return new ResponseEntity<String>("200", HttpStatus.OK);
    }
}
