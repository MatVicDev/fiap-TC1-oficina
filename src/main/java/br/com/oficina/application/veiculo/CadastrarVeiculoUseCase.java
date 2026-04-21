package br.com.oficina.application.veiculo;

import br.com.oficina.api.veiculo.dto.VeiculoInput;
import br.com.oficina.domain.veiculo.*;
import org.springframework.stereotype.Service;

@Service
public class CadastrarVeiculoUseCase {
    private final VeiculoRepository repository;

    public CadastrarVeiculoUseCase(VeiculoRepository repository) {
        this.repository = repository;
    }

    public void cadastrar(VeiculoInput input) {
        Placa placa = new Placa(input.placa());

        if (repository.existePorPlaca(placa)) {
            throw new RuntimeException("Veículo já cadastrado com esta placa.");
        }

        Veiculo veiculo = new Veiculo(
                placa,
                input.marca(),
                input.modelo(),
                input.ano(),
                input.cor(),
                input.cpfProprietario()
        );

        repository.salvar(veiculo);
    }
}