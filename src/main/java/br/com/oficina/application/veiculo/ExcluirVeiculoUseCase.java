package br.com.oficina.application.veiculo;

import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
import br.com.oficina.domain.veiculo.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExcluirVeiculoUseCase {
    private final VeiculoRepository repository;

    public void executar(UUID id) {
        repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Veículo não encontrado"));

        repository.excluir(id);
    }
}
