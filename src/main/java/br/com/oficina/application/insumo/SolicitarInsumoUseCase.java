package br.com.oficina.application.insumo;

import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SolicitarInsumoUseCase {
    private InsumoRepository insumoRepository;
    private EstoqueRepository estoqueRepository;

    public SolicitarInsumoUseCase(InsumoRepository insumoRepository, EstoqueRepository estoqueRepository) {
        this.insumoRepository = insumoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional
    public void solicitar(UUID insumoId, Integer quantidade) {
        Insumo insumo = insumoRepository.buscarPorId(insumoId)
                .orElseThrow(() -> new RuntimeException("Insumo não identificado"));

        Estoque estoque = estoqueRepository.buscarPorInsumoId(insumoId)
                .orElseThrow(() -> new RuntimeException("Peça não identificada"));

        estoque.reduzir(quantidade);

        estoqueRepository.salvar(estoque);
    }
}
