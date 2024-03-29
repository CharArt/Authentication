package com.converter.converter.auth.entity;

import com.converter.converter.auth.entity.repository.dto.RoleDTO;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles")
    private List<Users> users;

    @ManyToMany(mappedBy = "roles")
    private List<GoogleUser> googleUsers;

    public List<GoogleUser> getGoogleUsers() {
        return googleUsers;
    }

    public void setGoogleUsers(List<GoogleUser> googleUsers) {
        this.googleUsers = googleUsers;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Users> getUsers() {
        return this.users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public Roles() {
    }

    public Roles(RoleDTO rDTO) {
        if (Objects.equals(rDTO, null)) {
            throw new NullPointerException();
        }
        this.id = rDTO.getId();
        this.role = rDTO.getRole();
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.id != null ? id.hashCode() : 0);
        result = 31 * result + (this.role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Roles)) return false;

        Roles role = (Roles) o;

        if (this.id != role.id) return false;
        if (this.role != null ? !role.equals(role.role) : role.role != null) return false;
        return this.hashCode() == role.hashCode();
    }
}