package com.converter.converter.auth.service;

import com.converter.converter.auth.entity.Roles;
import com.converter.converter.auth.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RolesRepository repository;

    @Autowired
    public RoleServiceImpl(RolesRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Roles> findAll() {
        return repository.findAll();
    }

    @Override
    public Roles findByRoleName(String roleName) {
        return repository.findByRoleName(roleName);
    }

    @Override
    public void createdNewRole(Roles newRole) {
        repository.save(newRole);
    }

    @Override
    public List<Roles> getDefaultRole() {
        List<Roles> defaultRole = new ArrayList<>();
        defaultRole.add(repository.findByRoleName("USER"));
        return defaultRole;
    }
}
