package br.com.oficina.application.servico;

import br.com.oficina.api.servico.dto.AtualizarServicoRequest;
import br.com.oficina.api.servico.dto.ServicoResponse;
import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
import br.com.oficina.domain.servico.Servico;
import br.com.oficina.domain.servico.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AtualizarServicoUseCase {
    private final ServicoRepository repository;

    public ServicoResponse executar(UUID id, AtualizarServicoRequest request) {
        Servico servico = repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Serviço não encontrado"));

        servico.atualizar(
                request.nome(),
                request.descricao(),
                request.valor(),
                request.tempoPrevisto());

        repository.salvar(servico);

        return ServicoMapper.toResponse(servico);
    }
}
