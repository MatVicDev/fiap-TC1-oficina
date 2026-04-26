package br.com.oficina.api.cliente.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ClienteResponse(
        UUID id,
        String nome,
        String cpf,
        String telefone,
        String email,
        LocalDateTime dataCadastro
) {}
