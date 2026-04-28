package br.com.oficina.infrastructure.persistence.estoque;

import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstoqueRepositoryJpa extends JpaRepository<Estoque, UUID>, EstoqueRepository {

    @Override
    default Estoque salvar(Estoque estoque) {
        return save(estoque);
    }

    @Override
    default Optional<Estoque> buscarPorId(UUID id) {
        return findById(id);
    }

    @Override
    default List<Estoque> listarTodos() {
        return findAll();
    }

    @Override
    default void excluirPorInsumoId(UUID id) {
        deleteById(id);
    }

    @Override
    default Optional<Estoque> buscarPorInsumoId(UUID insumoId) {
        return findByInsumoId(insumoId);
    }

    Optional<Estoque> findByInsumoId(UUID insumoId);
}
