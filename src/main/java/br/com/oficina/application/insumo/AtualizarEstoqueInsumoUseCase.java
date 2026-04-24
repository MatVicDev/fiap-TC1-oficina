package br.com.oficina.application.insumo;

import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AtualizarEstoqueInsumoUseCase {
    private final InsumoRepository insumoRepository;
    private final EstoqueRepository estoqueRepository;

    public AtualizarEstoqueInsumoUseCase(InsumoRepository insumoRepository, EstoqueRepository estoqueRepository) {
        this.insumoRepository = insumoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional
    public void atualizar(UUID id, Integer quantidade) {
        Insumo insumo = insumoRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Insumo não encontrado!"));

        Estoque estoque = estoqueRepository.findByInsumoId(insumo.getId())
                        .orElseThrow(() -> new RuntimeException("Insumo não enscontrado no estoque!"));

        estoque.repor(quantidade);

        estoqueRepository.salvar(estoque);
    }
}
