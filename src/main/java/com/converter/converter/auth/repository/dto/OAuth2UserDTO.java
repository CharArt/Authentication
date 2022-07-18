package com.converter.converter.auth.repository.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class OAuth2UserDTO implements OAuth2User {
    private final List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
    private final OAuth2User user;

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

    @Override
    public Map<String, Object> getAttributes() {
        return this.user.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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