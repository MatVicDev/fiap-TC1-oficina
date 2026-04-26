package br.com.oficina.application.cliente;

import br.com.oficina.api.cliente.dto.CadastrarClienteRequest;
import br.com.oficina.domain.cliente.Cliente;
import br.com.oficina.domain.cliente.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarClienteUseCase {
    private final ClienteRepository repository;

    public Cliente executar(CadastrarClienteRequest request) {
        Cliente cliente = new Cliente(
                request.nome(),
                request.cpf(),
                request.telefone(),
                request.email());

        return repository.salvar(cliente);
    }
}
