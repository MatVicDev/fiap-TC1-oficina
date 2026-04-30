package br.com.oficina.application.insumo;

import br.com.oficina.api.exception.EntidadeNaoEncontradaException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ExcluirInsumoUseCase")
class ExcluirInsumoUseCaseTest {

    @Mock
    private InsumoRepository insumoRepository;

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private ExcluirInsumoUseCase useCase;

    @Test
    @DisplayName("Deve excluir insumo e estoque com sucesso")
    void deveExcluirInsumoEEstoqueComSucesso() {
        Insumo insumo = new Insumo(
                "Óleo Motor",
                "Descrição",
                new BigDecimal("45.90"),
                TipoInsumo.PRODUTO);

        when(insumoRepository.buscarPorId(any())).thenReturn(Optional.of(insumo));

        useCase.executar(UUID.randomUUID());

        verify(estoqueRepository, times(1)).excluirPorInsumoId(any());
        verify(insumoRepository, times(1)).excluir(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando insumo não for encontrado")
    void deveLancarExcecaoQuandoInsumoNaoEncontrado() {
        when(insumoRepository.buscarPorId(any())).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.executar(UUID.randomUUID()));

        verify(insumoRepository, never()).excluir(any());
    }
}