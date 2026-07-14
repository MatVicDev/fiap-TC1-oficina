package br.com.oficina.api.servico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AtualizarServicoRequest(

        @NotBlank
        String nome,

        String descricao,

        @NotNull
        BigDecimal precoBase,

        @NotNull
        Integer tempoPrevisto
) {}
