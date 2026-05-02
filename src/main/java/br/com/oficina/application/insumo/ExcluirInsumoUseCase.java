package br.com.oficina.application.insumo;

import br.com.oficina.domain.estoque.EstoqueRepository;
import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
import br.com.oficina.domain.insumo.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExcluirInsumoUseCase {
    private final InsumoRepository repository;
    private final EstoqueRepository estoqueRepository;

    public void executar(UUID id) {
        repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Insumo não encontrado"));

        estoqueRepository.excluirPorInsumoId(id);

        repository.excluir(id);
    }
}
