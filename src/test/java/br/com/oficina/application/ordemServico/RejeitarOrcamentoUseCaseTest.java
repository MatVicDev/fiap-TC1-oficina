package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import br.com.oficina.domain.ordemServico.StatusOS;
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
@DisplayName("Testes do RejeitarOrcamentoUseCase")
class RejeitarOrcamentoUseCaseTest {

    @Mock
    private OrdemServicoRepository repository;

    @Mock
    private NotificarStatusOrdemServicoService notificarStatusOrdemServicoService;

    @InjectMocks
    private RejeitarOrcamentoUseCase useCase;

    @Test
    @DisplayName("Deve rejeitar orçamento com sucesso")
    void deveRejeitarOrcamentoComSucesso() {
        OrdemServico os = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Barulho no motor");
        os.iniciarDiagnostico();
        os.finalizarDiagnostico();

        when(repository.buscarPorId(any())).thenReturn(Optional.of(os));
        when(repository.salvar(any())).thenAnswer(i -> i.getArgument(0));

        OrdemServicoResponse response = useCase.executar(os.getId());

        assertEquals(StatusOS.FINALIZADA, response.status());
        verify(repository, times(1)).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando OS não for encontrada")
    void deveLancarExcecaoQuandoOSNaoEncontrada() {
        when(repository.buscarPorId(any())).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.executar(UUID.randomUUID()));
    }
}