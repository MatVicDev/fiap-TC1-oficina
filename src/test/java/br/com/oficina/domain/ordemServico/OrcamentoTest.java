package br.com.oficina.domain.ordemServico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Testes da entidade Orcamento")
class OrcamentoTest {

    @Test
    @DisplayName("Deve criar orçamento com status PENDENTE")
    void deveCriarOrcamentoComStatusPendente() {
        Orcamento orcamento = new Orcamento(
                new BigDecimal("500.00"),
                StatusOrcamento.PENDENTE);

        assertNotNull(orcamento.getId());
        assertEquals(StatusOrcamento.PENDENTE, orcamento.getStatus());
        assertEquals(new BigDecimal("500.00"), orcamento.getValorTotal());
        assertNotNull(orcamento.getDataGeracao());
    }

    @Test
    @DisplayName("Deve criar orçamento aprovado")
    void deveCriarOrcamentoAprovado() {
        Orcamento orcamento = new Orcamento(
                new BigDecimal("500.00"),
                StatusOrcamento.APROVADO);

        assertEquals(StatusOrcamento.APROVADO, orcamento.getStatus());
    }

    @Test
    @DisplayName("Deve criar orçamento rejeitado")
    void deveCriarOrcamentoRejeitado() {
        Orcamento orcamento = new Orcamento(
                new BigDecimal("500.00"),
                StatusOrcamento.REJEITADO);

        assertEquals(StatusOrcamento.REJEITADO, orcamento.getStatus());
    }
}