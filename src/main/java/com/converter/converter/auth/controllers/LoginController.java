package com.converter.converter.auth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        Map<String, String> clientUrls = new HashMap<>();
        clientUrls.put("Google", "http://localhost:8080/oauth2/authorization/google");
        model.addAttribute("clientUrls", clientUrls);
        return "login";
    }
}
