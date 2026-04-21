package br.com.oficina.api.veiculo.dto;

public record VeiculoInput(
        String placa,
        String marca,
        String modelo,
        Integer ano,
        String cor,
        String cpfProprietario
) {}
