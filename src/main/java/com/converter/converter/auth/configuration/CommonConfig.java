package com.converter.converter.auth.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
@EnableAutoConfiguration
public class CommonConfig {

    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    @Value("${spring.mail.port}")
    private int port;
    @Value("${spring.mail.protocol}")
    private String protocol;
    @Value("${spring.mail.debug}")
    private String debug;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.setPort(port);
        javaMailSender.setProtocol(protocol);

        Properties property = javaMailSender.getJavaMailProperties();
        property.setProperty("mail.transport.protocol", protocol);
        property.setProperty("spring.mail.debug", debug);

        return javaMailSender;
    }
}
