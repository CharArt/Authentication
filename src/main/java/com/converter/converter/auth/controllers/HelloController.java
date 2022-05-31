package com.converter.converter.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Controller
public class HelloController {

    @Autowired
    private OAuth2AuthorizedClientService service;

    @GetMapping("/hello")
    public String loginPage(Model model, OAuth2AuthenticationToken auth) {
        OAuth2AuthorizedClient client = service.loadAuthorizedClient(auth.getAuthorizedClientRegistrationId(), auth.getName());
        String userInfoEndpointUri = "https://www.googleapis.com/oauth2/v3/userinfo";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "OAuth " + client.getAccessToken().getTokenValue());
        HttpEntity<Map> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
        Map userAttributes = response.getBody();
//        System.out.println(userAttributes.get("sub"));
//        System.out.println(userAttributes.get("name"));
//        System.out.println(userAttributes.get("given_name"));
//        System.out.println(userAttributes.get("family_name"));
//        System.out.println(userAttributes.get("email"));
//        System.out.println(userAttributes.get("email_verified"));
//        System.out.println(userAttributes.get("locale"));
        return "hello";
    }
}
