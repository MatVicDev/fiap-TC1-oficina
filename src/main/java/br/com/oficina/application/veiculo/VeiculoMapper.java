package br.com.oficina.application.veiculo;

import br.com.oficina.api.veiculo.dto.VeiculoResponse;
import br.com.oficina.domain.veiculo.Veiculo;

public class VeiculoMapper {

    public static VeiculoResponse toResponse(Veiculo veiculo) {
        return new  VeiculoResponse(
                veiculo.getId(),
                veiculo.getMarca(),
                veiculo.getModelo(),
                veiculo.getAno(),
                veiculo.getCor(),
                veiculo.getPlaca().getNumero(),
                veiculo.getCpfProprietario());
    }
}
