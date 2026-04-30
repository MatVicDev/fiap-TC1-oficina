package br.com.oficina.application.insumo;

import br.com.oficina.api.exception.EntidadeNaoEncontradaException;
import br.com.oficina.api.insumo.dto.AtualizarInsumoRequest;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para atualizar os insumos")
class AtualizarInsumoUseCaseTest {

    @Mock
    private InsumoRepository insumoRepository;

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private AtualizarInsumoUseCase useCase;

    @Test
    @DisplayName("Deve atualizar insumo com sucesso")
    void deveAtualizarInsumoComSucesso() {
        Insumo insumo = new Insumo(
                "Óleo Motor",
                "Descrição",
                new BigDecimal("45.90"),
                TipoInsumo.PRODUTO);

        Estoque estoque = new Estoque(insumo.getId(), 10);

        AtualizarInsumoRequest request = new AtualizarInsumoRequest(
                "Óleo Motor Premium",
                "Nova descrição",
                new BigDecimal("55.90"),
                TipoInsumo.PRODUTO);

        when(insumoRepository.buscarPorId(any())).thenReturn(Optional.of(insumo));
        when(estoqueRepository.buscarPorInsumoId(any())).thenReturn(Optional.of(estoque));
        when(insumoRepository.salvar(any())).thenAnswer(i -> i.getArgument(0));

        InsumoResponse response = useCase.executar(UUID.randomUUID(), request);

        assertNotNull(response);
        assertEquals("Óleo Motor Premium", response.nome());
        verify(insumoRepository, times(1)).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando insumo não for encontrado")
    void deveLancarExcecaoQuandoInsumoNaoEncontrado() {
        when(insumoRepository.buscarPorId(any())).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> useCase.executar(UUID.randomUUID(),
                        new AtualizarInsumoRequest("Nome", "Desc",
                                new BigDecimal("45.90"), TipoInsumo.PRODUTO)));
    }
}