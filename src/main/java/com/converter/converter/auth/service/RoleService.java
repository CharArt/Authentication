package com.converter.converter.auth.service;

import com.converter.converter.auth.entity.Roles;

import java.util.List;

public interface RoleService {
    List<Roles> findAll();

    Roles findByRoleId(Long roleId);

    Roles findByRoleName(String roleName);

    void createdNewRole(Roles newRole);
}
