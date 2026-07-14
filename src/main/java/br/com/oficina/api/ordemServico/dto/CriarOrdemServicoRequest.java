package br.com.oficina.api.ordemServico.dto;

import jakarta.validation.constraints.NotBlank;

public record CriarOrdemServicoRequest(

        @NotBlank
        String cpfCliente,

        @NotBlank
        String placaVeiculo,

        @NotBlank
        String sintomaRelatado
) {}
