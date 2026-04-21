package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.AbrirOSInput;
import br.com.oficina.domain.cliente.Cliente;
import br.com.oficina.domain.ordemServico.*;
import br.com.oficina.domain.cliente.Cpf;
import br.com.oficina.domain.veiculo.Placa;
import br.com.oficina.domain.veiculo.Veiculo;
import br.com.oficina.domain.veiculo.VeiculoRepository;
import br.com.oficina.domain.cliente.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AbrirOrdemServicoUseCase {
    private final OrdemServicoRepository osRepository;
    private final VeiculoRepository veiculoRepository;
    private final ClienteRepository clienteRepository;

    public AbrirOrdemServicoUseCase(
            OrdemServicoRepository osRepository,
            VeiculoRepository veiculoRepository,
            ClienteRepository clienteRepository) {

        this.osRepository = osRepository;
        this.veiculoRepository = veiculoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public UUID abrir(AbrirOSInput input) {
        Cliente cliente = clienteRepository.buscarPorCpf(new Cpf(input.cpfCliente()))
                .orElseThrow(() -> new RuntimeException("Cliente não cadastrado."));

        Veiculo veiculo = veiculoRepository.buscarPorPlaca(new Placa(input.placaVeiculo()))
                .orElseThrow(() -> new RuntimeException("Veículo não identificado no sistema."));

        OrdemServico novaOS = new OrdemServico(
                cliente.getId(),
                veiculo.getId(),
                input.sintomaRelatado()
        );

        OrdemServico ordemServico = osRepository.salvar(novaOS);

        return ordemServico.getId();
    }
}