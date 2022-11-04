package com.converter.converter.auth.controllers;

import com.converter.converter.auth.entity.repository.dto.CaptchaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response";
    private final RestTemplate restTemplate;

    @Value("${recaptcha.secret.key}")
    private String secret;

    @Autowired
    public LoginController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/login")
    public String login(ModelMap model) {
        Map<String, String> clientUrls = new HashMap<>();
        clientUrls.put("Google", "http://localhost:8080/oauth2/authorization/google");
        model.addAttribute("clientUrls", clientUrls);
        return "login";
    }

    @PostMapping("/login")
    public String loginPage(ModelMap model, @RequestParam("g-recaptcha-response") String captchaResponse) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);

        CaptchaResponseDTO response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDTO.class);
        if (!response.isSuccess()) {
            model.addAttribute("captchaError, Fill captcha");
        }

        return "login";
    }
}
