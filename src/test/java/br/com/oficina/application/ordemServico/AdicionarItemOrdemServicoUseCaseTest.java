package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import br.com.oficina.domain.insumo.TipoInsumo;
import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
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
@DisplayName("Testes do AdicionarItemOrdemServicoUseCase")
class AdicionarItemOrdemServicoUseCaseTest {

    @Mock
    private OrdemServicoRepository osRepository;

    @Mock
    private InsumoRepository insumoRepository;

    @Mock
    private EstoqueRepository estoqueRepository;

    @InjectMocks
    private AdicionarItemOrdemServicoUseCase useCase;

    @Test
    @DisplayName("Deve adicionar item à OS com sucesso")
    void deveAdicionarItemOSComSucesso() {
        OrdemServico os = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Barulho no motor");

        Insumo insumo = new Insumo(
                "Óleo Motor",
                "Óleo sintético",
                new BigDecimal("45.90"),
                TipoInsumo.PRODUTO);

        Estoque estoque = new Estoque(insumo.getId(), 10);

        when(osRepository.buscarPorId(any())).thenReturn(Optional.of(os));
        when(insumoRepository.buscarPorId(any())).thenReturn(Optional.of(insumo));
        when(estoqueRepository.buscarPorInsumoId(any())).thenReturn(Optional.of(estoque));
        when(osRepository.salvar(any())).thenAnswer(i -> i.getArgument(0));

        OrdemServicoResponse reponse = useCase.executar(os.getId(), insumo.getId(), 2);

        assertNotNull(reponse);
        assertEquals(new BigDecimal("91.80"), reponse.valorTotal());
        verify(estoqueRepository, times(1)).salvar(any());
        verify(osRepository, times(1)).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando o estoque for insuficiente")
    void deveLancarExcecaoQuandoEstoqueInsuficiente() {
        OrdemServico os = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Barulho no motor");

        Insumo insumo = new Insumo(
                "Óleo Motor", "Óleo sintético",
                new BigDecimal("45.90"), TipoInsumo.PRODUTO);

        Estoque estoque = new Estoque(insumo.getId(), 1);

        when(osRepository.buscarPorId(any())).thenReturn(Optional.of(os));
        when(insumoRepository.buscarPorId(any())).thenReturn(Optional.of(insumo));
        when(estoqueRepository.buscarPorInsumoId(any())).thenReturn(Optional.of(estoque));

        assertThrows(Exception.class, () -> useCase.executar(os.getId(), insumo.getId(), 10));
        verify(osRepository, never()).salvar(any());
    }
}