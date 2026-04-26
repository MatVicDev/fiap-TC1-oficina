package br.com.oficina.application.cliente;

import br.com.oficina.domain.cliente.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExcluirClienteUseCase {
    private final ClienteRepository repository;

    public void executar(UUID id) {
        repository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

        repository.excluir(id);
    }
}
