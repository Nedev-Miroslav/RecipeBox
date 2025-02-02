package com.example.recipebox.repository;

import com.example.recipebox.model.entity.Role;
import com.example.recipebox.model.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {
    Role findByRoleType(RoleType roleType);
}
