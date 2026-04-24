package br.com.oficina.infrastructure.persistence.veiculo;

import br.com.oficina.domain.veiculo.Veiculo;
import br.com.oficina.domain.veiculo.VeiculoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VeiculoRepositoryJpa extends JpaRepository<Veiculo, UUID>, VeiculoRepository {
}
