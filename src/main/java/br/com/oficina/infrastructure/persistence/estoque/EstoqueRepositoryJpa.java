package br.com.oficina.infrastructure.persistence.estoque;

import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EstoqueRepositoryJpa extends JpaRepository<Estoque, UUID>, EstoqueRepository {
}
