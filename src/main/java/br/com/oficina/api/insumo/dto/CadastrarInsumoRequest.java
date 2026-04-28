package br.com.oficina.api.insumo.dto;

import br.com.oficina.domain.insumo.TipoInsumo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CadastrarInsumoRequest(

        @NotBlank
        String nome,

        String descricao,

        @NotNull
        BigDecimal precoBase,

        @NotNull
        TipoInsumo tipo,

        @NotNull
        Integer quantidadeInicial
) {}
