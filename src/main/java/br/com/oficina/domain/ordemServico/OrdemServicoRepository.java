package br.com.oficina.domain.ordemServico;

import br.com.oficina.domain.cliente.Cpf;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdemServicoRepository {

    OrdemServico salvar(OrdemServico novaOS);

    Optional<OrdemServico> buscarPorId(UUID id);

    List<OrdemServico> listarOrdemServicos();

    List<OrdemServico> listarOrdemServicoPorStatus(StatusOS status);

    boolean existePorId(UUID id);
}
