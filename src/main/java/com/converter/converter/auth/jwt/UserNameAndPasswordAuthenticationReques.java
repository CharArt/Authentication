package com.converter.converter.auth.jwt;

public class UserNameAndPasswordAuthenticationReques {
    private String userName;
    private String password;

    public UserNameAndPasswordAuthenticationReques() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
