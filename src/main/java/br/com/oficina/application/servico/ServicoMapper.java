package br.com.oficina.application.servico;

import br.com.oficina.api.servico.dto.ServicoResponse;
import br.com.oficina.domain.servico.Servico;

public class ServicoMapper {

    public static ServicoResponse toResponse(Servico servico) {
        return new ServicoResponse(
                servico.getId(),
                servico.getNome(),
                servico.getDescricao(),
                servico.getPrecoBase(),
                servico.getTempoPrevisto());
    }
}
