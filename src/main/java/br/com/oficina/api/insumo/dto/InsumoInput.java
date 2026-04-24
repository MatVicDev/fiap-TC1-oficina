package br.com.oficina.api.insumo.dto;

import br.com.oficina.domain.insumo.TipoInsumo;

import java.math.BigDecimal;

public record InsumoInput(
        String nome,
        String descricao,
        BigDecimal precoBase,
        TipoInsumo tipo
) {}
