package br.com.oficina.api.ordemServico.dto;

import java.util.UUID;

public record AdicionarItemInput(
        UUID osId,
        UUID insumoId,
        Integer quantidade
) {}
