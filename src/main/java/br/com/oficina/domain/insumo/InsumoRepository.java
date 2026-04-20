package br.com.oficina.domain.insumo;

import java.util.List;
import java.util.Optional;

public interface InsumoRepository {

    Insumo salvar(Insumo insumo);

    Optional<Insumo> buscarPorId(Integer id);

    List<Insumo> listarTodos();

    void excluir(Long id);
}
