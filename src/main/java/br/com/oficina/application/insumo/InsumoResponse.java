package br.com.oficina.application.insumo;

import br.com.oficina.domain.insumo.TipoInsumo;

import java.math.BigDecimal;
import java.util.UUID;

public record InsumoResponse(
        UUID id,
        String nome,
        String descricao,
        BigDecimal precoBase,
        TipoInsumo tipo,
        Integer quantidadeEstoque
) {}
