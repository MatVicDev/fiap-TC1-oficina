package br.com.oficina.api.insumo.dto;

import br.com.oficina.api.exception.EntidadeNaoEncontradaException;
import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtualizarEstoqueUseCase {
    private final EstoqueRepository estoqueRepository;

    @Transactional
    public void repor(UUID insumoId, Integer quantidade) {
        Estoque estoque = estoqueRepository.buscarPorInsumoId(insumoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Estoque não encontrado"));

        estoque.repor(quantidade);

        estoqueRepository.salvar(estoque);
    }

    @Transactional
    public void reduzir(UUID insumoId, Integer quantidade) {
        Estoque estoque = estoqueRepository.buscarPorInsumoId(insumoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Estoque não encontrado"));

        estoque.reduzir(quantidade);

        estoqueRepository.salvar(estoque);
    }
}