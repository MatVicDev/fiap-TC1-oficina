package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.DecisaoOrcamento;
import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import br.com.oficina.domain.ordemServico.StatusOS;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ProcessarNotificacaoOrcamentoUseCase")
class ProcessarNotificacaoOrcamentoUseCaseTest {

    @Mock
    private AprovarOrcamentoUseCase aprovarOrcamentoUseCase;

    @Mock
    private RejeitarOrcamentoUseCase rejeitarOrcamentoUseCase;

    @InjectMocks
    private ProcessarNotificacaoOrcamentoUseCase useCase;

    @Test
    @DisplayName("Deve delegar para AprovarOrcamentoUseCase quando decisão é APROVADO")
    void deveDelegarParaAprovarQuandoDecisaoAprovado() {
        UUID id = UUID.randomUUID();
        OrdemServicoResponse response = new OrdemServicoResponse(
                id, UUID.randomUUID(), UUID.randomUUID(), StatusOS.EM_EXECUCAO,
                "Barulho no motor", BigDecimal.ZERO, null, null);

        when(aprovarOrcamentoUseCase.executar(id)).thenReturn(response);

        OrdemServicoResponse resultado = useCase.executar(id, DecisaoOrcamento.APROVADO);

        assertEquals(StatusOS.EM_EXECUCAO, resultado.status());
        verify(aprovarOrcamentoUseCase, times(1)).executar(id);
        verify(rejeitarOrcamentoUseCase, never()).executar(any(UUID.class));
    }

    @Test
    @DisplayName("Deve delegar para RejeitarOrcamentoUseCase quando decisão é REJEITADO")
    void deveDelegarParaRejeitarQuandoDecisaoRejeitado() {
        UUID id = UUID.randomUUID();
        OrdemServicoResponse response = new OrdemServicoResponse(
                id, UUID.randomUUID(), UUID.randomUUID(), StatusOS.FINALIZADA,
                "Barulho no motor", BigDecimal.ZERO, null, null);

        when(rejeitarOrcamentoUseCase.executar(id)).thenReturn(response);

        OrdemServicoResponse resultado = useCase.executar(id, DecisaoOrcamento.REJEITADO);

        assertEquals(StatusOS.FINALIZADA, resultado.status());
        verify(rejeitarOrcamentoUseCase, times(1)).executar(id);
        verify(aprovarOrcamentoUseCase, never()).executar(any(UUID.class));
    }
}
