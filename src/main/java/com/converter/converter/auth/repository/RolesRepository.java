package com.converter.converter.auth.repository;

import com.converter.converter.auth.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    @Query(value = "SELECT * FROM role WHERE role_name=?;", nativeQuery = true)
    Roles findByRoleName(String roleName);
}
