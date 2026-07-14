package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import br.com.oficina.domain.ordemServico.StatusOS;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de listagem das ordens de serviços")
class ListarOrdensDeServicoUseCaseTest {

    @Mock
    private OrdemServicoRepository repository;

    @InjectMocks
    private ListarOrdensDeServicoUseCase useCase;

    @Test
    @DisplayName("Deve listar todas as ordens de serviço")
    void deveListarTodasAsOrdensDeServico() {
        List<OrdemServico> ordens = List.of(
                new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Barulho no motor"),
                new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Troca de óleo"));

        when(repository.listarTodos()).thenReturn(ordens);

        List<OrdemServicoResponse> response = useCase.executar();

        assertEquals(2, response.size());
        verify(repository, times(1)).listarTodos();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há ordens de serviço")
    void deveRetornarListaVaziaQuandoNaoHaOrdens() {
        when(repository.listarTodos()).thenReturn(List.of());

        List<OrdemServicoResponse> response = useCase.executar();

        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("Deve excluir da listagem as OS finalizadas e entregues")
    void deveExcluirOSFinalizadasEEntregues() {
        OrdemServico recebida = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Recebida");

        OrdemServico finalizada = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Finalizada");
        finalizada.iniciarDiagnostico();
        finalizada.finalizarDiagnostico();
        finalizada.aprovarOrcamento();
        finalizada.finalizarServico();

        OrdemServico entregue = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Entregue");
        entregue.iniciarDiagnostico();
        entregue.finalizarDiagnostico();
        entregue.aprovarOrcamento();
        entregue.finalizarServico();
        entregue.registrarEntrega();

        when(repository.listarTodos()).thenReturn(List.of(recebida, finalizada, entregue));

        List<OrdemServicoResponse> response = useCase.executar();

        assertEquals(1, response.size());
        assertEquals(StatusOS.RECEBIDA, response.get(0).status());
    }

    @Test
    @DisplayName("Deve ordenar por prioridade de status e, dentro do mesmo status, mais antigas primeiro")
    void deveOrdenarPorPrioridadeDeStatus() throws InterruptedException {
        OrdemServico recebida = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Recebida");

        OrdemServico emDiagnostico = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Em diagnóstico");
        emDiagnostico.iniciarDiagnostico();

        OrdemServico aguardandoAprovacao = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Aguardando aprovação");
        aguardandoAprovacao.iniciarDiagnostico();
        aguardandoAprovacao.finalizarDiagnostico();

        OrdemServico emExecucaoAntiga = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Em execução antiga");
        emExecucaoAntiga.iniciarDiagnostico();
        emExecucaoAntiga.finalizarDiagnostico();
        emExecucaoAntiga.aprovarOrcamento();

        Thread.sleep(5);

        OrdemServico emExecucaoRecente = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Em execução recente");
        emExecucaoRecente.iniciarDiagnostico();
        emExecucaoRecente.finalizarDiagnostico();
        emExecucaoRecente.aprovarOrcamento();

        when(repository.listarTodos()).thenReturn(List.of(
                recebida, emDiagnostico, aguardandoAprovacao, emExecucaoRecente, emExecucaoAntiga));

        List<OrdemServicoResponse> response = useCase.executar();

        assertEquals(5, response.size());
        assertEquals(emExecucaoAntiga.getId(), response.get(0).id());
        assertEquals(emExecucaoRecente.getId(), response.get(1).id());
        assertEquals(StatusOS.AGUARDANDO_APROVACAO, response.get(2).status());
        assertEquals(StatusOS.EM_DIAGNOSTICO, response.get(3).status());
        assertEquals(StatusOS.RECEBIDA, response.get(4).status());
    }
}
