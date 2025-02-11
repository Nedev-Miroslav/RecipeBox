package com.example.recipebox.init;

import com.example.recipebox.model.entity.Role;
import com.example.recipebox.model.enums.RoleType;
import com.example.recipebox.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class InitRoles implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public InitRoles(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        long countRoles = this.roleRepository.count();

        if (countRoles != 0) {
            return;
        }

        Role admin = new Role();
        admin.setRoleType(RoleType.ADMIN);
        roleRepository.saveAndFlush(admin);
        Role user = new Role();
        user.setRoleType(RoleType.USER);
        roleRepository.saveAndFlush(user);


    }
}
