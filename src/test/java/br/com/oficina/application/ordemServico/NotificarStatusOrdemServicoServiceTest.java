package br.com.oficina.application.ordemServico;

import br.com.oficina.domain.cliente.Cliente;
import br.com.oficina.domain.cliente.ClienteRepository;
import br.com.oficina.domain.notificacao.NotificacaoPort;
import br.com.oficina.domain.ordemServico.OrdemServico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do NotificarStatusOrdemServicoService")
class NotificarStatusOrdemServicoServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private NotificacaoPort notificacaoPort;

    @InjectMocks
    private NotificarStatusOrdemServicoService service;

    @Test
    @DisplayName("Deve notificar quando cliente possui e-mail cadastrado")
    void deveNotificarQuandoClientePossuiEmail() {
        OrdemServico os = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Barulho no motor");
        Cliente cliente = new Cliente("Matheus", "12345678909", "41999999999", "matheus@email.com");

        when(clienteRepository.buscarPorId(os.getClienteId())).thenReturn(Optional.of(cliente));

        service.notificarMudancaStatus(os);

        verify(notificacaoPort, times(1)).notificar(eq("matheus@email.com"), anyString(), anyString());
    }

    @Test
    @DisplayName("Não deve notificar quando cliente não possui e-mail")
    void naoDeveNotificarQuandoClienteNaoPossuiEmail() {
        OrdemServico os = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Barulho no motor");
        Cliente cliente = new Cliente("Matheus", "12345678909", "41999999999", null);

        when(clienteRepository.buscarPorId(os.getClienteId())).thenReturn(Optional.of(cliente));

        service.notificarMudancaStatus(os);

        verify(notificacaoPort, never()).notificar(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Não deve notificar nem lançar exceção quando cliente não for encontrado")
    void naoDeveNotificarQuandoClienteNaoEncontrado() {
        OrdemServico os = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Barulho no motor");

        when(clienteRepository.buscarPorId(os.getClienteId())).thenReturn(Optional.empty());

        service.notificarMudancaStatus(os);

        verify(notificacaoPort, never()).notificar(anyString(), anyString(), anyString());
    }
}
