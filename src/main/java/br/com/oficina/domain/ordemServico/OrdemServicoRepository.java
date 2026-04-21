package br.com.oficina.domain.ordemServico;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdemServicoRepository {

    OrdemServico salvar(OrdemServico novaOS);

    Optional<OrdemServico> buscarPorId(UUID id);

    List<OrdemServico> listarOrdemServicos();

    boolean existePorId(UUID id);
}
