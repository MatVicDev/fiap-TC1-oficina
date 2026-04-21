package br.com.oficina.application.ordemServico;

import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ConcluirOrdemServicoUseCase {
    private final OrdemServicoRepository repository;

    public ConcluirOrdemServicoUseCase(OrdemServicoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void executar(UUID osId) {
        OrdemServico os = repository.buscarPorId(osId)
                .orElseThrow(() -> new RuntimeException("OS não encontrada."));

        os.finalizarServico();

        repository.salvar(os);
    }
}
