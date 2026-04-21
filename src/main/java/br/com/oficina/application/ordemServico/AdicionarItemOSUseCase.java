package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.AdicionarItemInput;
import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import br.com.oficina.domain.ordemServico.ItemOS;
import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdicionarItemOSUseCase {
    private final OrdemServicoRepository osRepository;
    private final InsumoRepository insumoRepository;

    public AdicionarItemOSUseCase(OrdemServicoRepository osRepository, InsumoRepository insumoRepository) {
        this.osRepository = osRepository;
        this.insumoRepository = insumoRepository;
    }

    @Transactional
    public void executar(AdicionarItemInput input) {
        OrdemServico os = osRepository.buscarPorId(input.osId())
                .orElseThrow(() -> new RuntimeException("Ordem de Serviço não encontrada."));

        Insumo insumo = insumoRepository.buscarPorId(input.insumoId())
                .orElseThrow(() -> new RuntimeException("Insumo não encontrado."));

        insumo.deduzirEstoque(input.quantidade());

        ItemOS itemOS = new ItemOS(insumo.getId(), insumo.getDescricao(), insumo.getPrecoBase(), input.quantidade());

        os.adicionarItemOs(itemOS);

        insumoRepository.salvar(insumo);
        osRepository.salvar(os);
    }
}
