package br.com.oficina.api.ordemServico.dto;

public record AbrirOSInput(
        String cpfCliente,
        String placaVeiculo,
        String sintomaRelatado
) {}
