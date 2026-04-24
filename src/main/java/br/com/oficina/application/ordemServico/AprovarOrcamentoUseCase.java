package br.com.oficina.application.ordemServico;

import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AprovarOrcamentoUseCase {
    private final OrdemServicoRepository osRepository;
    private final InsumoRepository insumoRepository;
    private final EstoqueRepository estoqueRepository;

    public AprovarOrcamentoUseCase(OrdemServicoRepository osRepository, InsumoRepository insumoRepository, EstoqueRepository estoqueRepository) {
        this.osRepository = osRepository;
        this.insumoRepository = insumoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional
    public void executar(UUID osId) {
        OrdemServico os = osRepository.buscarPorId(osId)
                .orElseThrow(() -> new RuntimeException("Ordem de Serviço não encontrada."));

        os.getItens().forEach(item -> {
            Optional<Insumo> insumo = insumoRepository.buscarPorId(item.getInsumoId());

            if (insumo.isPresent()) {
                Optional<Estoque> estoque = estoqueRepository.findByInsumoId(insumo.get().getId());

                if (estoque.isPresent()) {
                    estoque.get().repor(item.getQuantidade());
                    estoqueRepository.salvar(estoque.get());
                }
            }
        });

        os.aprovarOrcamento();

        osRepository.salvar(os);
    }
}
