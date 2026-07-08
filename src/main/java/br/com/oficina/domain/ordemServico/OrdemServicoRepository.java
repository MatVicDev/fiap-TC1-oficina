package br.com.oficina.domain.ordemServico;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdemServicoRepository {

    OrdemServico salvar(OrdemServico ordemServico);

    Optional<OrdemServico> buscarPorId(UUID id);

    List<OrdemServico> listarTodos();

    List<OrdemServico> listarOrdemServicosPorStatus(StatusOS status);

    boolean existePorId(UUID id);
}
