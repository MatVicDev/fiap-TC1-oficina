package br.com.oficina.infrastructure.persistence.servico;

import br.com.oficina.domain.servico.Servico;
import br.com.oficina.domain.servico.ServicoRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServicoRepositoryJpa extends JpaRepository<Servico, UUID>, ServicoRepository {

    @Override
    default Servico salvar(Servico servico) {
        return save(servico);
    }

    @Override
    default Optional<Servico> buscarPorId(UUID id) {
        return findById(id);
    }

    @Override
    default List<Servico> buscarTodos() {
        return findAll();
    }

    @Override
    default void excluir(UUID id) {
        deleteById(id);
    }
}
