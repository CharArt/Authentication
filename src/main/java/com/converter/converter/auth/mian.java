package com.converter.converter.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class mian {

    public static void main(String[] args) {

        String password = "Test1";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        password = encoder.encode(password);
        System.out.println(password);
        BCryptPasswordEncoder encoder1 = new BCryptPasswordEncoder(8);
        password = encoder1.encode(password);
        System.out.println(password);
        BCryptPasswordEncoder encoder2 = new BCryptPasswordEncoder(9);
        password = encoder2.encode(password);
        System.out.println(password);
        BCryptPasswordEncoder encoder3 = new BCryptPasswordEncoder(10);
        password = encoder3.encode(password);
        System.out.println(password);
        BCryptPasswordEncoder encoder4 = new BCryptPasswordEncoder(11);
        password = encoder4.encode(password);
        System.out.println(password);
    }
}
