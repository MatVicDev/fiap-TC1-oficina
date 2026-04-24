package br.com.oficina.application.insumo;

import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import br.com.oficina.domain.insumo.InsumoRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificarEstoqueUseCase {
    private final EstoqueRepository estoqueRepository;

    public VerificarEstoqueUseCase(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public Integer verificar(UUID insumoId) {
        return estoqueRepository.findByInsumoId(insumoId)
                .map(Estoque::getQuantidade)
                .orElseThrow(() -> new RuntimeException("Insumo não encontrado"));
    }
}
