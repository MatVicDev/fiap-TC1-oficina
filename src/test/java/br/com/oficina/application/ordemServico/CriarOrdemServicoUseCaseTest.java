package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.CriarOrdemServicoRequest;
import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import br.com.oficina.domain.cliente.Cliente;
import br.com.oficina.domain.cliente.ClienteRepository;
import br.com.oficina.domain.cliente.Cpf;
import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import br.com.oficina.domain.ordemServico.StatusOS;
import br.com.oficina.domain.veiculo.Veiculo;
import br.com.oficina.domain.veiculo.VeiculoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do CriarOrdemServicoUseCase")
class CriarOrdemServicoUseCaseTest {

    @Mock
    private OrdemServicoRepository osRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private NotificarStatusOrdemServicoService notificarStatusOrdemServicoService;

    @InjectMocks
    private CriarOrdemServicoUseCase useCase;

    @Test
    @DisplayName("Deve criar uma ordem de serviço com sucesso")
    void deveCriarOSComSucesso() {
        Cliente cliente = new Cliente("Matheus", "12345678909", "41999999999", "matheus@email.com");
        Veiculo veiculo = new Veiculo("ABC1D23", "Toyota", "Corolla", 2020, "Prata", "12345678901");

        CriarOrdemServicoRequest request = new CriarOrdemServicoRequest(
                "12345678909",
                "ABC1D23",
                "Barulho no motor");

        when(clienteRepository.buscarPorCpf(any(Cpf.class)))
                .thenReturn(Optional.of(cliente));

        when(veiculoRepository.buscarPorPlaca(any()))
                .thenReturn(Optional.of(veiculo));

        when(osRepository.salvar(any(OrdemServico.class)))
                .thenAnswer(i -> i.getArgument(0));

        OrdemServicoResponse response = useCase.executar(request);

        assertNotNull(response);
        assertEquals(StatusOS.RECEBIDA, response.status());
        verify(osRepository, times(1)).salvar(any(OrdemServico.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não for encontrado")
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        CriarOrdemServicoRequest request = new CriarOrdemServicoRequest(
                "12345678909",
                "ABC1D23",
                "Barulho no motor");

        when(clienteRepository.buscarPorCpf(any(Cpf.class)))
                .thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.executar(request));
        verify(osRepository, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando veículo não for encontrado")
    void deveLancarExcecaoQuandoVeiculoNaoEncontrado() {
        Cliente cliente = new Cliente("Matheus Victor", "12345678909", "41999999999", "matheus@email.com");

        CriarOrdemServicoRequest request = new CriarOrdemServicoRequest(
                "12345678909",
                "ABC1D23",
                "Barulho no motor");

        when(clienteRepository.buscarPorCpf(any(Cpf.class)))
                .thenReturn(Optional.of(cliente));

        when(veiculoRepository.buscarPorPlaca(any()))
                .thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.executar(request));
        verify(osRepository, never()).salvar(any());
    }
}