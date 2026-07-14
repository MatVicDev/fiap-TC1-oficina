package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
import br.com.oficina.domain.ordemServico.Orcamento;
import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import br.com.oficina.domain.ordemServico.StatusOrcamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConcluirDiagnosticoUseCase {
    private final OrdemServicoRepository repository;
    private final NotificarStatusOrdemServicoService notificarStatusOrdemServicoService;

    @Transactional
    public OrdemServicoResponse executar(UUID id) {
        OrdemServico os = repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("OS não encontrada"));

        os.finalizarDiagnostico();

        Orcamento orcamento = new Orcamento(os.getValorTotal(), StatusOrcamento.PENDENTE);
        os.setOrcamento(orcamento);

        repository.salvar(os);
        notificarStatusOrdemServicoService.notificarMudancaStatus(os);

        return OrdemServicoMapper.toResponse(os);
    }
}