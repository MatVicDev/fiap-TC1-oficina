package br.com.oficina.api.veiculo.dto;

public record VeiculoOutput(
        String numero,
        String marca,
        String modelo,
        String cpfProprietario
) {}
