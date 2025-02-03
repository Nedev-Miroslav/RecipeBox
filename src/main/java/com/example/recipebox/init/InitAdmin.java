package com.example.recipebox.init;

import com.example.recipebox.model.entity.Role;
import com.example.recipebox.model.entity.User;
import com.example.recipebox.model.enums.RoleType;
import com.example.recipebox.repository.RoleRepository;
import com.example.recipebox.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class InitAdmin implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public InitAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        long countUsers = this.userRepository.count();

        if (countUsers != 0) {
            return;
        }

        Role role = roleRepository.findByRoleType(RoleType.ADMIN);
        User user = new User();

        user.setUsername("Admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.getRoles().add(role);
        user.setEmail("recipebox@gmail.com");

        userRepository.save(user);
    }
}
