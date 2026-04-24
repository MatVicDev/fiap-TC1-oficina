package br.com.oficina.application.ordemServico;

import br.com.oficina.domain.ordemServico.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class IniciarDiagnosticoUseCase {
    private final OrdemServicoRepository repository;

    public IniciarDiagnosticoUseCase(OrdemServicoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void executar(UUID osId) {
        OrdemServico os = repository.buscarPorId(osId)
                .orElseThrow(() -> new RuntimeException("Ordem de Serviço não encontrada."));

        os.iniciarDiagnostico();

        repository.salvar(os);
    }
}