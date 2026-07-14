package br.com.oficina.application.veiculo;

import br.com.oficina.api.veiculo.dto.VeiculoResponse;
import br.com.oficina.domain.veiculo.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarVeiculosUseCase {
    private final VeiculoRepository repository;

    public List<VeiculoResponse> executar() {
        return repository.listarTodos().stream()
                .map(VeiculoMapper::toResponse)
                .toList();
    }
}
