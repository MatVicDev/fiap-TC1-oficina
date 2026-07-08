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
    private final InsumoRepository insumoRepository;
    private final EstoqueRepository estoqueRepository;

    public void executar(UUID id) {
        insumoRepository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Insumo não encontrado"));

        estoqueRepository.excluirPorInsumoId(id);

        insumoRepository.excluir(id);
    }
}
