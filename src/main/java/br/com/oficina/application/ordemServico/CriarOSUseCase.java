package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.CriarOSRequest;
import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import br.com.oficina.domain.cliente.Cliente;
import br.com.oficina.domain.cliente.ClienteRepository;
import br.com.oficina.domain.cliente.Cpf;
import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import br.com.oficina.domain.veiculo.Placa;
import br.com.oficina.domain.veiculo.Veiculo;
import br.com.oficina.domain.veiculo.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CriarOSUseCase {
    private final OrdemServicoRepository osRepository;
    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;

    @Transactional
    public OrdemServicoResponse executar(CriarOSRequest request) {
        Cliente cliente = clienteRepository.buscarPorCpf(new Cpf(request.cpfCliente()))
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado"));

        Veiculo veiculo = veiculoRepository.buscarPorPlaca(new Placa(request.placaVeiculo()))
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Veículo não encontrado"));

        OrdemServico os = new OrdemServico(
                cliente.getId(),
                veiculo.getId(),
                request.sintomaRelatado());

        osRepository.salvar(os);

        return OrdemServicoMapper.toResponse(os);
    }
}
