package br.com.oficina.application.insumo;

import br.com.oficina.api.insumo.dto.InsumoResponse;
import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import br.com.oficina.domain.insumo.TipoInsumo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para busca de insumos")
class BuscarInsumoUseCaseTest {

    @Mock
    private InsumoRepository insumoRepository;

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private BuscarInsumoUseCase useCase;

    @Test
    @DisplayName("Deve buscar insumo por ID com sucesso")
    void deveBuscarInsumoPorIdComSucesso() {
        Insumo insumo = new Insumo(
                "Óleo Motor",
                "Descrição",
                new BigDecimal("45.90"),
                TipoInsumo.PRODUTO);

        Estoque estoque = new Estoque(insumo.getId(), 10);

        when(insumoRepository.buscarPorId(any())).thenReturn(Optional.of(insumo));
        when(estoqueRepository.buscarPorInsumoId(any())).thenReturn(Optional.of(estoque));

        InsumoResponse response = useCase.executar(UUID.randomUUID());

        assertNotNull(response);
        assertEquals("Óleo Motor", response.nome());
        assertEquals(10, response.quantidadeEstoque());
    }

    @Test
    @DisplayName("Deve lançar exceção quando insumo não for encontrado")
    void deveLancarExcecaoQuandoInsumoNaoEncontrado() {
        when(insumoRepository.buscarPorId(any())).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.executar(UUID.randomUUID()));
    }
}