package br.com.oficina.api.cliente.dto;

public record AtualizarClienteRequest(
        String telefone,
        String email
) {}
