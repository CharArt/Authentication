package com.converter.converter.auth.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "google_users")
public class GoogleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "sub")
    private String sub;

    @Column(name = "given_name")
    private String given_name;

    @Column(name = "family_name")
    private String family_name;

    @Column(name = "email")
    private String email;

    @Column(name = "email_verified")
    private boolean email_verified;

    @Column(name = "locale")
    private String locale;

    @OneToOne
    @JoinColumn(name = "id")
    private Users user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "google_users_roles", joinColumns = @JoinColumn(name = "google_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private List<Roles> roles;

    public GoogleUser() {
    }

    public GoogleUser(Long user_id, String sub, String given_name, String family_name, String email, boolean email_verified, String locale, Users user) {
        this.user_id = user_id;
        this.sub = sub;
        this.given_name = given_name;
        this.family_name = family_name;
        this.email = email;
        this.email_verified = email_verified;
        this.locale = locale;
        this.user = user;
    }

    public GoogleUser(OAuth2User oAuth2User, Users users) {
        if (!users.isEmpty()) {
            this.user_id = users.getId();
            this.user = users;
        }
        if (oAuth2User.getAttribute("sub") != null) {
            this.sub = oAuth2User.getAttribute("sub");
        }
        if (oAuth2User.getAttribute("given_name") != null) {
            this.given_name = oAuth2User.getAttribute("given_name");
        }
        if (oAuth2User.getAttribute("family_name") != null) {
            this.family_name = oAuth2User.getAttribute("family_name");
        }
        if (oAuth2User.getAttribute("email") != null) {
            this.email = oAuth2User.getAttribute("email");
        }
        if (Boolean.TRUE.equals(oAuth2User.getAttribute("email_verified"))) {
            this.email_verified = true;
        }
        if (oAuth2User.getAttribute("locale") != null) {
            this.locale = oAuth2User.getAttribute("locale");
        }
        this.roles = users.getRoles();
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getGiven_name() {
        return given_name;
    }

    public void setGiven_name(String given_name) {
        this.given_name = given_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmail_verified() {
        return false;
    }

    public void setEmail_verified(boolean email_verified) {
        this.email_verified = false;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<Roles> getRolesList() {
        return roles;
    }

    public void setRolesList(List<Roles> rolesList) {
        this.roles = rolesList;
    }


    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.id != null ? id.hashCode() : 0);
        result = 31 * result + (this.sub != null ? sub.hashCode() : 0);
        result = 31 * result + (this.given_name != null ? given_name.hashCode() : 0);
        result = 31 * result + (this.family_name != null ? family_name.hashCode() : 0);
        result = 31 * result + (this.email != null ? email.hashCode() : 0);
        result = 31 * result + (this.locale != null ? locale.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof GoogleUser googleUser)) return false;

        if (!Objects.equals(this.id, googleUser.id)) return false;
        if (!Objects.equals(this.sub, googleUser.sub)) return false;
        if (!Objects.equals(this.given_name, googleUser.given_name)) return false;
        if (!Objects.equals(this.family_name, googleUser.family_name)) return false;
        if (!Objects.equals(this.email, googleUser.email)) return false;
        if (!Objects.equals(this.locale, googleUser.locale)) return false;
        return !Objects.equals(this.hashCode(), googleUser.hashCode());
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        joiner.add(this.id.toString());
        joiner.add(this.sub);
        joiner.add(this.given_name);
        joiner.add(this.family_name);
        joiner.add(this.email);
        joiner.add(this.locale);
        joiner.add(this.user.toString());
        return joiner.toString();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRolesList()
                .stream()
                .map(roles1 -> {
                    SimpleGrantedAuthority SGA = new SimpleGrantedAuthority("ROLE_" + roles1.getRole());
                    return SGA;
                }).collect(Collectors.toList());
    }

    public boolean isEmpty() {
        return this.getSub().isEmpty() || this.getEmail().isEmpty() || this.getGiven_name().isEmpty() || this.getFamily_name().isEmpty() ;
    }
}