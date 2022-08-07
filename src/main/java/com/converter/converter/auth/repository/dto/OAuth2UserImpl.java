package com.converter.converter.auth.repository.dto;

import com.converter.converter.auth.entity.GoogleUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class OAuth2UserImpl implements OAuth2User {
    private final OAuth2User oAuth2User;
    private GoogleUser googleUser;

    public OAuth2UserImpl(OAuth2User user) {
        this.oAuth2User = user;
    }

    public OAuth2UserImpl(OAuth2User user, GoogleUser googleUser) {
        this.oAuth2User = user;
        this.googleUser = googleUser;
    }

    public String getSub() {
        return oAuth2User.getAttribute("sub");
    }

    public String getGiven_name() {
        return oAuth2User.getAttribute("given_name");
    }

    public String getFamily_name() {
        return oAuth2User.getAttribute("family_name");
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }

    public boolean isEmail_verified() {
        return Boolean.TRUE.equals(oAuth2User.getAttribute("email_verified"));
    }

    public String getLocale() {
        return oAuth2User.getAttribute("locale");
    }

    public OAuth2User getoAuth2User() {
        return oAuth2User;
    }

    public GoogleUser getGoogleUser() {
        return googleUser;
    }

    public void setGoogleUser(GoogleUser googleUser) {
        this.googleUser = googleUser;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (googleUser == null) {
            return this.oAuth2User.getAuthorities();
        }
        return googleUser.getAuthorities();
    }

    @Override
    public String getName() {
        return this.oAuth2User.getName();
    }

    @Override
    public <A> A getAttribute(String name) {
        return this.oAuth2User.getAttribute(name);
    }
}