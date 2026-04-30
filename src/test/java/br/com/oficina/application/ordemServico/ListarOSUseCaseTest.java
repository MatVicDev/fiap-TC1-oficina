package br.com.oficina.application.ordemServico;

import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import br.com.oficina.domain.ordemServico.OrdemServico;
import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes de listagem das ordens de serviços")
class ListarOSUseCaseTest {

    @Mock
    private OrdemServicoRepository repository;

    @InjectMocks
    private ListarOSUseCase useCase;

    @Test
    @DisplayName("Deve listar todas as ordens de serviço")
    void deveListarTodasAsOrdensDeServico() {
        List<OrdemServico> ordens = List.of(
                new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Barulho no motor"),
                new OrdemServico(UUID.randomUUID(), UUID.randomUUID(), "Troca de óleo"));

        when(repository.listarOrdemServicos()).thenReturn(ordens);

        List<OrdemServicoResponse> response = useCase.executar();

        assertEquals(2, response.size());
        verify(repository, times(1)).listarOrdemServicos();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há ordens de serviço")
    void deveRetornarListaVaziaQuandoNaoHaOrdens() {
        when(repository.listarOrdemServicos()).thenReturn(List.of());

        List<OrdemServicoResponse> response = useCase.executar();

        assertTrue(response.isEmpty());
    }
}