package br.com.oficina.application.veiculo;

import br.com.oficina.api.veiculo.dto.CadastrarVeiculoRequest;
import br.com.oficina.api.veiculo.dto.VeiculoResponse;
import br.com.oficina.domain.veiculo.Veiculo;
import br.com.oficina.domain.veiculo.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarVeiculoUseCase {
    private final VeiculoRepository repository;

    public VeiculoResponse executar(CadastrarVeiculoRequest request) {
        Veiculo veiculo = new Veiculo(
                request.placa(),
                request.marca(),
                request.modelo(),
                request.ano(),
                request.cor(),
                request.cpfProprietario());

        repository.salvar(veiculo);

        return VeiculoMapper.toResponse(veiculo);
    }
}