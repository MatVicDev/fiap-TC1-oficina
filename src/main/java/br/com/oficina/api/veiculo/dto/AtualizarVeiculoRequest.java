package br.com.oficina.api.veiculo.dto;

public record AtualizarVeiculoRequest(
        String marca,
        String modelo,
        Integer ano,
        String cor
) {}
