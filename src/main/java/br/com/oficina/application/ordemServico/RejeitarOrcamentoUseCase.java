package br.com.oficina.application.ordemServico;

import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class RejeitarOrcamentoUseCase {
    private final OrdemServicoRepository osRepository;
    private final InsumoRepository insumoRepository;

    public RejeitarOrcamentoUseCase(OrdemServicoRepository osRepository, InsumoRepository insumoRepository) {
        this.osRepository = osRepository;
        this.insumoRepository = insumoRepository;
    }

    @Transactional
    public void executar(UUID osId) {
        OrdemServico os = osRepository.buscarPorId(osId)
                .orElseThrow(() -> new RuntimeException("Ordem de Serviço não encontrada."));

        os.getItens().forEach(item -> {
            Optional<Insumo> insumo = insumoRepository.buscarPorId(item.getInsumoId());

            if (insumo.isPresent()) {
                insumo.get().reporEstoque(item.getQuantidade());
                insumoRepository.salvar(insumo.get());
            }
        });

        os.rejeitarOrcamento();

        osRepository.salvar(os);
    }
}
