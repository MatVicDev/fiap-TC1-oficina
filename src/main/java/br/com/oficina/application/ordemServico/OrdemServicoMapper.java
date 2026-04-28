package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import br.com.oficina.domain.ordemServico.OrdemServico;

public class OrdemServicoMapper {
    public static OrdemServicoResponse toResponse(OrdemServico os) {
        return new OrdemServicoResponse(
                os.getId(),
                os.getClienteId(),
                os.getVeiculoId(),
                os.getStatus(),
                os.getSintomaRelatado(),
                os.getValorTotal(),
                os.getDataInicio(),
                os.getDataEntrega());
    }
}
