package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.AdicionarItemInput;
import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import br.com.oficina.domain.insumo.TipoInsumo;
import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import br.com.oficina.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdicionarItemOSUseCaseTest {

    @Mock
    private OrdemServicoRepository ordemServicoRepository;

    @Mock
    private InsumoRepository insumoRepository;

    @InjectMocks
    private AdicionarItemOSUseCase useCase;

    private UUID ordemServicoId;
    private UUID insumoId;
    private OrdemServico ordemServico;
    private Insumo insumo;

    @BeforeEach
    void setUp() {
        ordemServicoId = UUID.randomUUID();
        insumoId = UUID.randomUUID();
        ordemServico = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Troca de óleo");
        insumo = new Insumo("Óleo 5W30", new BigDecimal("50.00"), TipoInsumo.PRODUTO, 10);
    }

    @Test
    @DisplayName("Deve adicionar um item com sucesso e deduzir estoque")
    void deveAdicionarItemComSucesso() {
        AdicionarItemInput input = new AdicionarItemInput(ordemServicoId, insumoId, 2);

        when(ordemServicoRepository.buscarPorId(ordemServicoId)).thenReturn(Optional.of(ordemServico));
        when(insumoRepository.buscarPorId(insumoId)).thenReturn(Optional.of(insumo));

        useCase.executar(input);

        assertEquals(8, insumo.getQuantidadeEstoque());
        assertEquals(1, ordemServico.getItens().size());
        assertEquals(new BigDecimal("100.00"), ordemServico.getValorTotal());

        verify(insumoRepository).salvar(insumo);
        verify(ordemServicoRepository).salvar(ordemServico);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o estoque for insuficiente")
    void deveLancarExcecaoEstoqueInsuficiente() {
        AdicionarItemInput input = new AdicionarItemInput(ordemServicoId, insumoId, 15);

        when(ordemServicoRepository.buscarPorId(ordemServicoId)).thenReturn(Optional.of(ordemServico));
        when(insumoRepository.buscarPorId(insumoId)).thenReturn(Optional.of(insumo));

        assertThrows(DomainException.class, () ->  useCase.executar(input));

        verify(ordemServicoRepository, never()).salvar(any());
    }
}