package br.com.oficina.application.servico;

import br.com.oficina.api.exception.EntidadeNaoEncontradaException;
import br.com.oficina.domain.servico.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExcluirServicoUseCase {
    private final ServicoRepository repository;

    public void executar(UUID id) {
        repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Serviço não encontrado"));

        repository.excluir(id);
    }
}
