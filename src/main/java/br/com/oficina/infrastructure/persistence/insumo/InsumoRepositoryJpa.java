package br.com.oficina.infrastructure.persistence.insumo;

import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InsumoRepositoryJpa extends JpaRepository<Insumo, UUID>, InsumoRepository {
}
