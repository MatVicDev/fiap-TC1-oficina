package br.com.oficina.application.servico;

import br.com.oficina.api.servico.dto.ServicoResponse;
import br.com.oficina.domain.servico.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarServicosUseCase {
    private final ServicoRepository repository;

    public List<ServicoResponse> executar() {
        return repository.buscarTodos().stream()
                .map(ServicoMapper::toResponse)
                .toList();
    }
}
