package br.com.oficina.application.ordemServico;

import br.com.oficina.api.exception.EntidadeNaoEncontradaException;
import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
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
@DisplayName("Testes do IniciarDiagnosticoUseCase")
class IniciarDiagnosticoUseCaseTest {

    @Mock
    private OrdemServicoRepository repository;

    @InjectMocks
    private IniciarDiagnosticoUseCase useCase;

    @Test
    @DisplayName("Deve iniciar diagnóstico com sucesso")
    void deveIniciarDiagnosticoComSucesso() {
        OrdemServico os = new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Barulho no motor");

        when(repository.buscarPorId(any())).thenReturn(Optional.of(os));
        when(repository.salvar(any())).thenAnswer(i -> i.getArgument(0));

        OrdemServicoResponse response = useCase.executar(os.getId());

        assertEquals(StatusOS.EM_DIAGNOSTICO, response.status());
        verify(repository, times(1)).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a OS não for encontrada")
    void deveLancarExcecaoQuandoOSNaoEncontrada() {
        when(repository.buscarPorId(any())).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.executar(UUID.randomUUID()));
    }
}