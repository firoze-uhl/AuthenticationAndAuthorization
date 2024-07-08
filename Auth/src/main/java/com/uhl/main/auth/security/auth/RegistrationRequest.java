package com.uhl.main.auth.security.auth;

public record RegistrationRequest(
        String firstName,
        String lastName,
        String username,
        String password
) {
}
