package com.converter.converter.auth.controllers.rest;

import com.converter.converter.auth.entity.Users;
import com.converter.converter.auth.jwt.JwtTokenProvider;
import com.converter.converter.auth.repository.dto.AuthenticationUserDTO;
import com.converter.converter.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user/auth")
public class UserRestAuthorization {
    private final JwtTokenProvider provider;
    //    private final AuthenticationManager manager;
    private final UserService userService;

    public UserRestAuthorization(JwtTokenProvider provider/*, AuthenticationManager manager*/, UserService userService) {
        this.provider = provider;
//        this.manager = manager;
        this.userService = userService;
    }

//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody AuthenticationUserDTO userDTO) {
//        Users user = userService.findUserByLogin(userDTO.getLogin());
//        manager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getLogin(), userDTO.getPassword()));
//        String token = provider.createToken(userDTO.getLogin(), user.getRoles());
//        Map<Object, Object> response = new HashMap<>();
//        response.put("login", userDTO.getLogin());
//        response.put("token", token);
//        return ResponseEntity.ok();
//    }

    @PostMapping("/login")
    public void login(@RequestBody AuthenticationUserDTO userDTO) {

    }
}
