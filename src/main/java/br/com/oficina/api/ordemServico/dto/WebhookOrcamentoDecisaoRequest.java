package br.com.oficina.api.ordemServico.dto;

import jakarta.validation.constraints.NotNull;

public record WebhookOrcamentoDecisaoRequest(@NotNull DecisaoOrcamento decisao) {}
