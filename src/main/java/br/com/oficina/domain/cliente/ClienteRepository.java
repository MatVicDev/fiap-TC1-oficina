package br.com.oficina.domain.cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {

    Cliente salvar(Cliente cliente);

    Optional<Cliente> buscarPorCpf(Cpf cpf);

    List<Cliente> buscarClientes();

    boolean existePorCpf(Cpf cpf);
}
