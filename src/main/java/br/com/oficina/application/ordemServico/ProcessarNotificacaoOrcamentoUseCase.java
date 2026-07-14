package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.DecisaoOrcamento;
import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProcessarNotificacaoOrcamentoUseCase {
    private final AprovarOrcamentoUseCase aprovarOrcamentoUseCase;
    private final RejeitarOrcamentoUseCase rejeitarOrcamentoUseCase;

    public OrdemServicoResponse executar(UUID id, DecisaoOrcamento decisao) {
        return switch (decisao) {
            case APROVADO -> aprovarOrcamentoUseCase.executar(id);
            case REJEITADO -> rejeitarOrcamentoUseCase.executar(id);
        };
    }
}
