package br.com.oficina.domain.estoque;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstoqueRepository {

    Estoque salvar(Estoque estoque);

    Optional<Estoque> buscarPorId(UUID id);

    List<Estoque> listarTodos();

    void excluir(Long id);

    Optional<Estoque> findByInsumoId(UUID insumoId);
}
