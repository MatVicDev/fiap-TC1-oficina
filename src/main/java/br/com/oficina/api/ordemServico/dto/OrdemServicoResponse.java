package br.com.oficina.api.ordemServico.dto;

import br.com.oficina.domain.ordemServico.StatusOS;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrdemServicoResponse(
        UUID id,
        UUID clienteId,
        UUID veiculoId,
        StatusOS status,
        String sintomaRelatado,
        BigDecimal valorTotal,
        LocalDateTime dataInicio,
        LocalDateTime dataEntrega
) {}
