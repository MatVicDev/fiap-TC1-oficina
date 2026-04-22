package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.AbrirOSInput;
import br.com.oficina.domain.cliente.Cliente;
import br.com.oficina.domain.cliente.ClienteRepository;
import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import br.com.oficina.domain.ordemServico.StatusOS;
import br.com.oficina.domain.veiculo.Veiculo;
import br.com.oficina.domain.veiculo.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AbrirOrdemServicoUseCaseTest {

    @Mock
    private OrdemServicoRepository ordemServicoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private AbrirOrdemServicoUseCase useCase;

    @Captor
    private ArgumentCaptor<OrdemServico> ordemServicoCaptor;

    private String cpfCliente;
    private String placaVeiculo;
    private String descricao;

    @BeforeEach
    void setUp() {
        cpfCliente = "111.222.333-44";
        placaVeiculo = "ABC-1234";
        descricao = "Barulho na suspensão dianteira";
    }

    @Test
    @DisplayName("Deve abrir uma nova Ordem de Serviço com status RECEBIDA")
    void deveAbrirOrdemServicoComSucesso() {
        AbrirOSInput input = new AbrirOSInput(cpfCliente, placaVeiculo, descricao);

        when(clienteRepository.buscarPorCpf(any())).thenReturn(Optional.of(mock(Cliente.class)));
        when(veiculoRepository.buscarPorPlaca(any())).thenReturn(Optional.of(mock(Veiculo.class)));
        when(ordemServicoRepository.salvar(any(OrdemServico.class))).thenAnswer(i -> i.getArgument(0));

        useCase.excecutar(input);

        verify(ordemServicoRepository).salvar(ordemServicoCaptor.capture());
        OrdemServico ordemServicoSalva = ordemServicoCaptor.getValue();

        assertNotNull(ordemServicoSalva.getId());
        assertEquals(StatusOS.RECEBIDA, ordemServicoSalva.getStatus());
        assertTrue(ordemServicoSalva.getItens().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o cliente não for encontrado")
    void deveLancarExcecaoQuandoOClienteNaoForEncontrado() {
        AbrirOSInput input = new AbrirOSInput(cpfCliente, placaVeiculo, descricao);

        when(clienteRepository.buscarPorCpf(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> useCase.excecutar(input));

        verify(ordemServicoRepository, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o veículo não for encontrado")
    void deveLancarExcecaoQuandoOVeiculoNaoForEncontrado() {
        AbrirOSInput input = new AbrirOSInput(cpfCliente, placaVeiculo, descricao);

        when(clienteRepository.buscarPorCpf(any())).thenReturn(Optional.of(mock(Cliente.class)));
        when(veiculoRepository.buscarPorPlaca(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> useCase.excecutar(input));

        verify(ordemServicoRepository, never()).salvar(any());
    }
}