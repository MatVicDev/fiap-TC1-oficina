package br.com.oficina.domain.insumo;

import br.com.oficina.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes da entidade Insumo")
class InsumoTest {

    @Test
    @DisplayName("Deve criar insumo com sucesso")
    void deveCriarInsumoComSucesso() {
        Insumo insumo = new Insumo(
                "Óleo Motor",
                "Descrição",
                new BigDecimal("45.90"),
                TipoInsumo.PRODUTO);

        assertNotNull(insumo.getId());
        assertEquals("Óleo Motor", insumo.getNome());
        assertEquals(TipoInsumo.PRODUTO, insumo.getTipo());
    }

    @Test
    @DisplayName("Deve lançar exceção para preço negativo")
    void deveLancarExcecaoParaPrecoNegativo() {
        assertThrows(DomainException.class, () ->
                new Insumo("Óleo Motor",
                        "Descrição",
                        new BigDecimal("-10.00"),
                        TipoInsumo.PRODUTO));
    }

    @Test
    @DisplayName("Deve atualizar insumo com sucesso")
    void deveAtualizarInsumoComSucesso() {
        Insumo insumo = new Insumo(
                "Óleo Motor",
                "Descrição",
                new BigDecimal("45.90"),
                TipoInsumo.PRODUTO);

        insumo.atualizar("Óleo Premium",
                "Nova descrição",
                new BigDecimal("55.90"),
                TipoInsumo.PRODUTO);

        assertEquals("Óleo Premium", insumo.getNome());
        assertEquals(new BigDecimal("55.90"), insumo.getPrecoBase());
    }
}