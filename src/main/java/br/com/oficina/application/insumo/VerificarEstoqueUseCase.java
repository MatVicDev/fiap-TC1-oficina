package br.com.oficina.application.insumo;

import br.com.oficina.domain.insumo.InsumoRepository;
import org.springframework.stereotype.Service;

@Service
public class VerificarEstoqueUseCase {
    private final InsumoRepository insumoRepository;

    public VerificarEstoqueUseCase(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }

    public Integer verificar(Long insumoId) {
        return insumoRepository.buscarPorId(insumoId)
                .map(insumo -> insumo.getSaldoEstoque())
                .orElseThrow(() -> new RuntimeException("Insumo não encontrado"));
    }
}
