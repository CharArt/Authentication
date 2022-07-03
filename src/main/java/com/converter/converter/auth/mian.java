package com.converter.converter.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class mian {

//    private static final OAuth2AuthorizedClientService oAuthservice;
//    @Autowired
//    public mian(OAuth2AuthorizedClientService oAuthservice) {
//        this.oAuthservice = oAuthservice;
//    }

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
//----------------------------------------------------------------------------------------------------------------------
//        OAuth2AuthorizedClient client = oAuthservice.loadAuthorizedClient(auth.getAuthorizedClientRegistrationId(), auth.getName());
//        String userInfoEndpointUri = "https://www.googleapis.com/oauth2/v3/userinfo";
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.AUTHORIZATION, "OAuth " + client.getAccessToken().getTokenValue());
//        HttpEntity<Map<String, String>> entity = new HttpEntity<>(headers);
//        ResponseEntity<Map> response = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
//        Map userAttributes = response.getBody();
//        if (!userAttributes.isEmpty()) {
//            System.out.println(userAttributes.get("sub"));
//            System.out.println(userAttributes.get("name"));
//            System.out.println(userAttributes.get("given_name"));
//            System.out.println(userAttributes.get("family_name"));
//            System.out.println(userAttributes.get("email"));
//            System.out.println(userAttributes.get("email_verified"));
//            System.out.println(userAttributes.get("locale"));
//        }
//    }
    }
}
