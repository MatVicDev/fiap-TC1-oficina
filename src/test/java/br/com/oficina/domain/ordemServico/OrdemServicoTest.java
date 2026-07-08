package br.com.oficina.domain.ordemServico;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Testes da OrdemServico")
class OrdemServicoTest {
    private OrdemServico ordemServico;
    private UUID clienteId;
    private UUID veiculoId;

    @BeforeEach
    void setUp() {
        clienteId = UUID.randomUUID();
        veiculoId = UUID.randomUUID();
        ordemServico = new OrdemServico(clienteId, veiculoId, "Barulho no motor");
    }

    @Test
    @DisplayName("Deve criar OS com status RECEBIDA")
    void deveCriarOSComStatusRecebida() {
        assertEquals(StatusOS.RECEBIDA, ordemServico.getStatus());
    }

    @Test
    @DisplayName("Deve iniciar diagnóstico")
    void deveIniciarDiagnostico() {
        ordemServico.iniciarDiagnostico();
        assertEquals(StatusOS.EM_DIAGNOSTICO, ordemServico.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção ao iniciar diagnóstico com status inválido")
    void deveLancarExcecaoAoIniciarDiagnosticoComStatusInvalido() {
        ordemServico.iniciarDiagnostico();
        assertThrows(IllegalStateException.class, () -> ordemServico.iniciarDiagnostico());
    }

    @Test
    @DisplayName("Deve aprovar orçamento")
    void deveAprovarOrcamento() {
        ordemServico.iniciarDiagnostico();
        ordemServico.finalizarDiagnostico();
        ordemServico.aprovarOrcamento();
        assertEquals(StatusOS.EM_EXECUCAO, ordemServico.getStatus());
    }

    @Test
    @DisplayName("Deve rejeitar orçamento")
    void deveRejeitarOrcamento() {
        ordemServico.iniciarDiagnostico();
        ordemServico.finalizarDiagnostico();
        ordemServico.rejeitarOrcamento();
        assertEquals(StatusOS.FINALIZADA, ordemServico.getStatus());
    }

    @Test
    @DisplayName("Deve finalizar serviço")
    void deveFinalizarServico() {
        ordemServico.iniciarDiagnostico();
        ordemServico.finalizarDiagnostico();
        ordemServico.aprovarOrcamento();
        ordemServico.finalizarServico();
        assertEquals(StatusOS.FINALIZADA, ordemServico.getStatus());
    }

    @Test
    @DisplayName("Deve registrar entrega")
    void deveRegistrarEntrega() {
        ordemServico.iniciarDiagnostico();
        ordemServico.finalizarDiagnostico();
        ordemServico.aprovarOrcamento();
        ordemServico.finalizarServico();
        ordemServico.registrarEntrega();
        assertEquals(StatusOS.ENTREGUE, ordemServico.getStatus());
    }

    @Test
    @DisplayName("Deve adicionar item e recalcular valor total")
    void deveAdicionarItemERecalcularValorTotal() {
        ItemOS item = new ItemOS(
                UUID.randomUUID(),
                "Óleo Motor",
                new BigDecimal("50.00"),
                2,
                new OrdemServico());

        ordemServico.adicionarItemOS(item);

        assertEquals(new BigDecimal("100.00"), ordemServico.getValorTotal());
    }

    @Test
    @DisplayName("Não deve adicionar item com status inválido")
    void naoDeveAdicionarItemComStatusInvalido() {
        ordemServico.iniciarDiagnostico();
        ordemServico.finalizarDiagnostico();
        ordemServico.aprovarOrcamento();

        ItemOS item = new ItemOS(
                UUID.randomUUID(),
                "Óleo Motor",
                new BigDecimal("50.00"),
                2,
                new OrdemServico());

        assertThrows(Exception.class, () -> ordemServico.adicionarItemOS(item));
    }
}