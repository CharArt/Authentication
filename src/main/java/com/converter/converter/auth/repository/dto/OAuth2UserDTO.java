package com.converter.converter.auth.repository.dto;

import com.converter.converter.auth.entity.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OAuth2UserDTO implements OAuth2User {
    private final OAuth2User user;

    private List<Roles> roles;

    public OAuth2UserDTO(OAuth2User oAuth2User) {
        this.user = oAuth2User;
    }

    public String getSub() {
        return user.getAttribute("sub");
    }

    public String getGiven_name() {
        return user.getAttribute("given_name");
    }

    public String getFamily_name() {
        return user.getAttribute("family_name");
    }

    public String getEmail() {
        return user.getAttribute("email");
    }

    public boolean isEmail_verified() {
        return Boolean.TRUE.equals(user.getAttribute("email_verified"));
    }

    public String getLocale() {
        return user.getAttribute("locale");
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.user.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles()
                .stream()
                .map(roles1 -> {
                    return new SimpleGrantedAuthority("ROLE_" + roles1.getRole());
                }).collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return this.user.getName();
    }

    @Override
    public <A> A getAttribute(String name) {
        return this.user.getAttribute(name);
    }
}