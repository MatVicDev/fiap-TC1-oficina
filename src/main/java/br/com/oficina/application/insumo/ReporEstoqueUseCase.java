package br.com.oficina.application.insumo;

import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReporEstoqueUseCase {
    private final EstoqueRepository estoqueRepository;

    @Transactional
    public void executar(UUID insumoId, Integer quantidade) {
        Estoque estoque = estoqueRepository.buscarPorInsumoId(insumoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Estoque não encontrado"));

        estoque.repor(quantidade);

        estoqueRepository.salvar(estoque);
    }
}
