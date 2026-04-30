package br.com.oficina.domain.estoque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Testes do Estoque")
class EstoqueTest {
    private Estoque estoque;

    @BeforeEach
    void setUp() {
        estoque = new Estoque(UUID.randomUUID(), 10);
    }

    @Test
    @DisplayName("Deve reduzir estoque corretamente")
    void deveReduzirEstoqueCorretamente() {
        estoque.reduzir(3);
        assertEquals(7, estoque.getQuantidade());
    }

    @Test
    @DisplayName("Deve repor estoque corretamente")
    void deveReporEstoqueCorretamente() {
        estoque.repor(5);
        assertEquals(15, estoque.getQuantidade());
    }

    @Test
    @DisplayName("Deve lançar exceção quando estoque insuficiente")
    void deveLancarExcecaoQuandoEstoqueInsuficiente() {
        assertThrows(IllegalArgumentException.class, () -> estoque.reduzir(11));
    }

    @Test
    @DisplayName("Deve zerar estoque completamente")
    void deveZerarEstoqueCompletamente() {
        estoque.reduzir(10);
        assertEquals(0, estoque.getQuantidade());
    }
}