package br.com.oficina.infrastructure.persistence.cliente;

import br.com.oficina.domain.cliente.Cliente;
import br.com.oficina.domain.cliente.ClienteRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClienteRepositoryJpa extends JpaRepository<Cliente, UUID>, ClienteRepository {
}
