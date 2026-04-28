package br.com.oficina.application.servico;

import br.com.oficina.api.servico.dto.CadastrarServicoRequest;
import br.com.oficina.api.servico.dto.ServicoResponse;
import br.com.oficina.domain.servico.Servico;
import br.com.oficina.domain.servico.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarServicoUseCase {
    private final ServicoRepository repository;

    public ServicoResponse executar(CadastrarServicoRequest request) {
        Servico servico = new Servico(
                request.nome(),
                request.descricao(),
                request.valor(),
                request.tempoPrevisto());

        repository.salvar(servico);

        return ServicoMapper.toResponse(servico);
    }
}
