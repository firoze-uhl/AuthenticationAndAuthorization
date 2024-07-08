package com.uhl.main.auth.security.user;

import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    public void initUser() {
        initRoles();

        createUserIfNotExists("admin", "Md. Firoze", "Hossain", "ADMIN", "admin");
        createUserIfNotExists("master", "Md. Firoze", "Ahmed", "MASTER", "master");
    }

    @Transactional
    public void initRoles() {
        createRoleIfNotExists("USER");
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("MASTER");
    }

    private void createRoleIfNotExists(String roleName) {
        if (roleRepository.findByName(roleName).isEmpty()) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
        }
    }

    private void createUserIfNotExists(String username, String firstName, String lastName, String roleName, String password) {
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new IllegalStateException("ROLE " + roleName + " was not initiated"));
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setRoles(List.of(role));
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
    }
}