package br.com.oficina.infrastructure.persistence.ordemServico;

import br.com.oficina.domain.ordemServico.OrdemServico;

public interface OrdemServicoRepositoryJpa {

    void salvar(OrdemServico ordemServico);
}
