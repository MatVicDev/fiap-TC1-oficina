package br.com.oficina.domain.veiculo;

import java.util.Optional;
import java.util.List;

public interface VeiculoRepository {

    Veiculo salvar(Veiculo veiculo);

    Optional<Veiculo> buscarPorPlaca(Placa placa);

    List<Veiculo> listarPorCpfProprietario(String cpf);

    boolean existePorPlaca(Placa placa);
}