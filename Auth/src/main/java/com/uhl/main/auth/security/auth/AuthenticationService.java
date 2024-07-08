package com.uhl.main.auth.security.auth;

import com.uhl.main.auth.security.config.JwtService;
import com.uhl.main.auth.security.user.Role;
import com.uhl.main.auth.security.user.RoleRepository;
import com.uhl.main.auth.security.user.User;
import com.uhl.main.auth.security.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public AuthenticationService(
            UserRepository userRepository,
            JwtService jwtService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }


    public AuthenticationResponse register(RegistrationRequest registerRequest) {
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));
        User user = new User();
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());
        user.setUsername(registerRequest.username());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setRoles(List.of(userRole));
        userRepository.save(user);
        String s = jwtService.generateToken(user);
        return new AuthenticationResponse(s);

    }

    public AuthenticationResponse registerAdmin(RegistrationRequest registerRequest) {
        Role userRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));
        User user = new User();
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());
        user.setUsername(registerRequest.username());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setRoles(List.of(userRole));
        userRepository.save(user);
        String s = jwtService.generateToken(user);
        return new AuthenticationResponse(s);

    }

}
