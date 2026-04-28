package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarOSUseCase {
    private final OrdemServicoRepository repository;

    public List<OrdemServicoResponse> executar() {
        return repository.listarOrdemServicos().stream()
                .map(OrdemServicoMapper::toResponse)
                .toList();
    }
}
