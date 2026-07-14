package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import br.com.oficina.domain.ordemServico.ItemOS;
import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdicionarItemOrdemServicoUseCase {
    private final OrdemServicoRepository osRepository;
    private final InsumoRepository insumoRepository;
    private final EstoqueRepository estoqueRepository;

    @Transactional
    public OrdemServicoResponse executar(UUID osId, UUID insumoId, Integer quantidade) {
        OrdemServico os = osRepository.buscarPorId(osId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("OS não encontrada"));

        Insumo insumo = insumoRepository.buscarPorId(insumoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Insumo não encontrado"));

        Estoque estoque = estoqueRepository.buscarPorInsumoId(insumoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Estoque não encontrado"));

        estoque.reduzir(quantidade);

        estoqueRepository.salvar(estoque);

        ItemOS item = new ItemOS(insumoId, insumo.getNome(), insumo.getPrecoBase(), quantidade, os);
        os.adicionarItemOS(item);

        osRepository.salvar(os);

        return OrdemServicoMapper.toResponse(os);
    }
}
