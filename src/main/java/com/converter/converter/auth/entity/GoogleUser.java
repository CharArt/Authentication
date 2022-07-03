package com.converter.converter.auth.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "google_user")
public class GoogleUser {

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "google_users_roles", joinColumns = @JoinColumn(name = "users_id"), inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private List<Roles> roles;
}



