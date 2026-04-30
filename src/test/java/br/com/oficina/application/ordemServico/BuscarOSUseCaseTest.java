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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para buscar ordens de serviço")
class BuscarOSUseCaseTest {

    @Mock
    private OrdemServicoRepository repository;

    @InjectMocks
    private BuscarOSUseCase useCase;

    @Test
    @DisplayName("Deve buscar OS por ID com sucesso")
    void deveBuscarOSPorIdComSucesso() {
        OrdemServico os = new OrdemServico(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Barulho no motor");

        when(repository.buscarPorId(any())).thenReturn(Optional.of(os));

        OrdemServicoResponse response = useCase.executar(UUID.randomUUID());

        assertNotNull(response);
        assertEquals(StatusOS.RECEBIDA, response.status());
    }

    @Test
    @DisplayName("Deve lançar exceção quando a ordem de serviço não for encontrada")
    void deveLancarExcecaoQuandoOSNaoEncontrada() {
        when(repository.buscarPorId(any())).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.executar(UUID.randomUUID()));
    }
}