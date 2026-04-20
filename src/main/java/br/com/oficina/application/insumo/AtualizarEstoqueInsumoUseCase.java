package br.com.oficina.application.insumo;

import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtualizarEstoqueInsumoUseCase {
    private final InsumoRepository insumoRepository;

    public AtualizarEstoqueInsumoUseCase(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }

    @Transactional
    public void atualizar(Long id, Integer quantidade) {
        Insumo insumo = insumoRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Insumo não encontrado!"));

        insumo.reporEstoque(quantidade);

        insumoRepository.salvar(insumo);
    }
}
