package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import br.com.oficina.domain.ordemServico.StatusOS;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarOrdensDeServicoUseCase {

    private static final List<StatusOS> ORDEM_EXIBICAO = List.of(
            StatusOS.EM_EXECUCAO, StatusOS.AGUARDANDO_APROVACAO,
            StatusOS.EM_DIAGNOSTICO, StatusOS.RECEBIDA);

    private final OrdemServicoRepository repository;

    public List<OrdemServicoResponse> executar() {
        return repository.listarTodos().stream()
                .filter(os -> os.getStatus() != StatusOS.FINALIZADA && os.getStatus() != StatusOS.ENTREGUE)
                .sorted(Comparator.comparing((OrdemServico os) -> ORDEM_EXIBICAO.indexOf(os.getStatus()))
                        .thenComparing(OrdemServico::getDataInicio))
                .map(OrdemServicoMapper::toResponse)
                .toList();
    }
}
