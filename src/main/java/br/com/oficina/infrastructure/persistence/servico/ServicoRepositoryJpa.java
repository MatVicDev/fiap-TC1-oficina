package br.com.oficina.infrastructure.persistence.servico;

import br.com.oficina.domain.servico.Servico;
import br.com.oficina.domain.servico.ServicoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServicoRepositoryJpa extends JpaRepository<Servico, UUID>, ServicoRepository {
}
