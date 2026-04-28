package br.com.oficina.infrastructure.security;

public record LoginRequest(
        String username,
        String password
) {}
