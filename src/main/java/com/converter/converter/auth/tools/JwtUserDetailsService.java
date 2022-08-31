package com.converter.converter.auth.tools;

import com.converter.converter.auth.entity.Users;
import com.converter.converter.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private UserService service;

    @Autowired
    public JwtUserDetailsService(UserService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users users = service.findUserByLogin(login);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("User with username: " + login + " not found");
        }
        return users;
    }
}
