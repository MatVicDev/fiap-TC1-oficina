package br.com.oficina.infrastructure.security;

public record LoginRequest(
        String usuario,
        String senha
) {}
