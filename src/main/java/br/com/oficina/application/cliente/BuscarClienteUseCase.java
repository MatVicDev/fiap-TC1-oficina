package br.com.oficina.application.cliente;

import br.com.oficina.api.cliente.dto.ClienteResponse;
import br.com.oficina.domain.cliente.Cliente;
import br.com.oficina.domain.cliente.ClienteRepository;
import br.com.oficina.domain.cliente.Cpf;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarClienteUseCase {
    private final ClienteRepository repository;

    public ClienteResponse executar(UUID id) {
        Cliente cliente = repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não identificado"));

        return ClienteMapper.toResponse(cliente);
    }

    public ClienteResponse executar(Cpf cpf) {
        Cliente cliente = repository.buscarPorCpf(cpf)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não identificado"));

        return ClienteMapper.toResponse(cliente);
    }
}
