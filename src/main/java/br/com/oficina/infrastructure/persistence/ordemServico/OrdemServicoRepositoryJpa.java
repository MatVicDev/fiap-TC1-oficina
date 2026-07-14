package br.com.oficina.infrastructure.persistence.ordemServico;

import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import br.com.oficina.domain.ordemServico.StatusOS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrdemServicoRepositoryJpa extends JpaRepository<OrdemServico, UUID>, OrdemServicoRepository {

    @Override
    default OrdemServico salvar(OrdemServico ordemServico) {
        return save(ordemServico);
    }

    @Override
    default Optional<OrdemServico> buscarPorId(UUID id) {
        return findById(id);
    }

    @Override
    default List<OrdemServico> listarTodos() {
        return findAll();
    }

    @Override
    default List<OrdemServico> listarOrdemServicosPorStatus(StatusOS status) {
        return findByStatus(status);
    }

    List<OrdemServico> findByStatus(StatusOS status);

    @Override
    default boolean existePorId(UUID id) {
        return existsById(id);
    }
}
