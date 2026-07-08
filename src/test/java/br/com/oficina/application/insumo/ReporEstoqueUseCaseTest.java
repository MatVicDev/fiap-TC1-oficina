package br.com.oficina.application.insumo;

import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ReporEstoqueUseCase")
class ReporEstoqueUseCaseTest {

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private ReporEstoqueUseCase useCase;

    @Test
    @DisplayName("Deve repor estoque com sucesso")
    void deveReporEstoqueComSucesso() {
        Estoque estoque = new Estoque(UUID.randomUUID(), 10);

        when(estoqueRepository.buscarPorInsumoId(any()))
                .thenReturn(Optional.of(estoque));

        useCase.executar(UUID.randomUUID(), 5);

        assertEquals(15, estoque.getQuantidade());
        verify(estoqueRepository, times(1)).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando estoque não encontrado")
    void deveLancarExcecaoQuandoEstoqueNaoEncontrado() {
        when(estoqueRepository.buscarPorInsumoId(any()))
                .thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> useCase.executar(UUID.randomUUID(), 5));
    }
}
