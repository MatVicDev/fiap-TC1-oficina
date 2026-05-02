package br.com.oficina.application.servico;

import br.com.oficina.api.servico.dto.ServicoResponse;
import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
import br.com.oficina.domain.servico.Servico;
import br.com.oficina.domain.servico.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarServicoUseCase {
    private final ServicoRepository repository;

    public ServicoResponse executar(UUID id) {
        Servico servico = repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Serviço não encontrado"));

        return ServicoMapper.toResponse(servico);
    }
}
