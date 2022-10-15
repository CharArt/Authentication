package com.converter.converter.auth.entity.repository.dto;

public class AuthenticationUserDTO {
    private String username;
    private String password;

    public AuthenticationUserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthenticationUserDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
