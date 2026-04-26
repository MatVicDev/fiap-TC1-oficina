package br.com.oficina.infrastructure.persistence.insumo;

import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InsumoRepositoryJpa extends JpaRepository<Insumo, UUID>, InsumoRepository {

    @Override
    default Insumo salvar(Insumo insumo) {
        return save(insumo);
    }

    @Override
    default Optional<Insumo> buscarPorId(UUID id) {
        return findById(id);
    }

    @Override
    default List<Insumo> listarTodos() {
        return findAll();
    }

    @Override
    default void excluir(UUID id) {
        deleteById(id);
    }
}
