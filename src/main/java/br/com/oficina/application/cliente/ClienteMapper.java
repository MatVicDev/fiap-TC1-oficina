package br.com.oficina.application.cliente;

import br.com.oficina.api.cliente.dto.ClienteResponse;
import br.com.oficina.domain.cliente.Cliente;

public class ClienteMapper {
    public static ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpfNumero(),
                cliente.getTelefone(),
                cliente.getEmail(),
                cliente.getStatus(),
                cliente.getDataCadastro());
    }
}
