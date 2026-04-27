package br.com.oficina.api.veiculo.dto;

import java.util.UUID;

public record VeiculoResponse(
        UUID id,
        String marca,
        String modelo,
        Integer ano,
        String cor,
        String placa,
        String cpfProprietario
) {}
