package com.example.recipebox.model.entity;

import com.example.recipebox.model.enums.RoleType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Role extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public Role() {
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType name) {
        this.roleType = name;
    }
}
