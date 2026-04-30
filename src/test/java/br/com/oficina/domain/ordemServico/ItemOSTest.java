package br.com.oficina.domain.ordemServico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Testes do ItemOS")
class ItemOSTest {

    @Test
    @DisplayName("Deve calcular preço total corretamente")
    void deveCalcularPrecoTotalCorretamente() {
        ItemOS item = new ItemOS(
                UUID.randomUUID(), "Óleo Motor",
                new BigDecimal("50.00"), 3);

        assertEquals(new BigDecimal("150.00"), item.getPrecoTotal());
    }

    @Test
    @DisplayName("Deve lançar exceção para quantidade zero")
    void deveLancarExcecaoParaQuantidadeZero() {
        assertThrows(IllegalArgumentException.class, () ->
                new ItemOS(UUID.randomUUID(), "Óleo Motor",
                        new BigDecimal("50.00"), 0));
    }

    @Test
    @DisplayName("Deve lançar exceção para quantidade negativa")
    void deveLancarExcecaoParaQuantidadeNegativa() {
        assertThrows(IllegalArgumentException.class, () ->
                new ItemOS(UUID.randomUUID(), "Óleo Motor",
                        new BigDecimal("50.00"), -1));
    }
}