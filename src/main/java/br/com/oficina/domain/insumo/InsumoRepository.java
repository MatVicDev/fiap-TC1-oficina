package br.com.oficina.domain.insumo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InsumoRepository {

    Insumo salvar(Insumo insumo);

    Optional<Insumo> buscarPorId(UUID id);

    List<Insumo> listarTodos();

    void excluir(Long id);
}
