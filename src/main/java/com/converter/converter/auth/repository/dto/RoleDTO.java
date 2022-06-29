package com.converter.converter.auth.repository.dto;

import com.converter.converter.auth.entity.Roles;

public class RoleDTO {
    private Long id;
    private String role;

    public RoleDTO(Roles roles) {
        this.id = roles.getId();
        this.role = roles.getRole();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
