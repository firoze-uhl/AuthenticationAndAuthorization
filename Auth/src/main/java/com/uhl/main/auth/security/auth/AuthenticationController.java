package com.uhl.main.auth.security.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {
        authenticationService.register(registrationRequest);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/sUser")
    public ResponseEntity<?> create(@RequestBody RegistrationRequest sUserDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: " + auth.getAuthorities());

        authenticationService.registerAdmin(sUserDTO);
        return ResponseEntity.accepted().build();
    }

}
