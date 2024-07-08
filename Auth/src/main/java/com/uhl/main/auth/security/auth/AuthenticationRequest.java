package com.uhl.main.auth.security.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
