package br.com.oficina.api.servico.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ServicoResponse(
        UUID id,
        String nome,
        String descricao,
        BigDecimal precoBase,
        Integer tempoPrevisto
) {}
