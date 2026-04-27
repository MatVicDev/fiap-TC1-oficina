package br.com.oficina.domain.veiculo;

import br.com.oficina.domain.cliente.Cpf;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VeiculoRepository {

    Veiculo salvar(Veiculo veiculo);

    Optional<Veiculo> buscarPorPlaca(Placa placa);

    Optional<Veiculo> buscarPorid(UUID id);

    List<Veiculo> listarVeiculos();

    List<Veiculo> listarPorCpfProprietario(Cpf cpf);

    boolean existePorPlaca(Placa placa);

    void excluir(UUID id);
}