package br.com.oficina.application.insumo;

import br.com.oficina.api.insumo.dto.CadastrarInsumoRequest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do CadastrarInsumoUseCase")
class CadastrarInsumoUseCaseTest {

    @Mock
    private InsumoRepository insumoRepository;

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private CadastrarInsumoUseCase useCase;

    @Test
    @DisplayName("Deve cadastrar insumo com estoque inicial")
    void deveCadastrarInsumoComEstoqueInicial() {
        CadastrarInsumoRequest request = new CadastrarInsumoRequest(
                "Óleo Motor 5W30",
                "Óleo sintético",
                new BigDecimal("45.90"),
                TipoInsumo.PRODUTO,
                50);

        when(insumoRepository.salvar(any(Insumo.class)))
                .thenAnswer(i -> i.getArgument(0));

        when(estoqueRepository.salvar(any(Estoque.class)))
                .thenAnswer(i -> i.getArgument(0));

        InsumoResponse reponse = useCase.executar(request);

        assertNotNull(reponse);
        assertEquals("Óleo Motor 5W30", reponse.nome());
        assertEquals(50, reponse.quantidadeEstoque());
        verify(insumoRepository, times(1)).salvar(any(Insumo.class));
        verify(estoqueRepository, times(1)).salvar(any(Estoque.class));
    }

    @Test
    @DisplayName("Deve lançar exceção para preço negativo")
    void deveLancarExcecaoParaPrecoNegativo() {
        CadastrarInsumoRequest request = new CadastrarInsumoRequest(
                "Óleo Motor 5W30",
                "Óleo sintético",
                new BigDecimal("-10.00"),
                TipoInsumo.PRODUTO,
                50);

        assertThrows(Exception.class, () -> useCase.executar(request));
        verify(insumoRepository, never()).salvar(any());
    }
}