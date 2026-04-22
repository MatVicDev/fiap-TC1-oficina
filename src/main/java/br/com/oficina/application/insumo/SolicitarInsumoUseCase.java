package br.com.oficina.application.insumo;

import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SolicitarInsumoUseCase {
    private InsumoRepository insumoRepository;

    public SolicitarInsumoUseCase(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }

    @Transactional
    public void solicitar(UUID insumoId, Integer quantidade) {
        Insumo insumo = insumoRepository.buscarPorId(insumoId)
                .orElseThrow(() -> new RuntimeException("Peça não identificada"));

        insumo.deduzirEstoque(quantidade);

        insumoRepository.salvar(insumo);
    }
}
