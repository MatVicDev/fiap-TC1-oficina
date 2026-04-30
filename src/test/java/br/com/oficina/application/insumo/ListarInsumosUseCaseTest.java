package br.com.oficina.application.insumo;

import br.com.oficina.api.insumo.dto.InsumoResponse;
import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de listagem dos insumos")
class ListarInsumosUseCaseTest {

    @Mock
    private InsumoRepository insumoRepository;

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private ListarInsumosUseCase useCase;

    @Test
    @DisplayName("Deve listar todos os insumos")
    void deveListarTodosOsInsumos() {
        Insumo insumo = new Insumo(
                "Óleo Motor",
                "Descrição",
                new BigDecimal("45.90"),
                TipoInsumo.PRODUTO);

        Estoque estoque = new Estoque(insumo.getId(), 10);

        when(insumoRepository.listarTodos()).thenReturn(List.of(insumo));
        when(estoqueRepository.buscarPorInsumoId(any())).thenReturn(Optional.of(estoque));

        List<InsumoResponse> response = useCase.executar();

        assertEquals(1, response.size());
        assertEquals(10, response.getFirst().quantidadeEstoque());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há insumos")
    void deveRetornarListaVaziaQuandoNaoHaInsumos() {
        when(insumoRepository.listarTodos()).thenReturn(List.of());

        List<InsumoResponse> response = useCase.executar();

        assertTrue(response.isEmpty());
    }
}