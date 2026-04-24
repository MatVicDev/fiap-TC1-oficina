package br.com.oficina.infrastructure.persistence.ordemServico;

import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrdemServicoRepositoryJpa extends JpaRepository<OrdemServico, UUID>, OrdemServicoRepository {

}
