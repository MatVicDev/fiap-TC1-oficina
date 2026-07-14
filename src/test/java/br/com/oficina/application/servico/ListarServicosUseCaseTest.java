package br.com.oficina.application.servico;

import br.com.oficina.api.servico.dto.ServicoResponse;
import br.com.oficina.domain.servico.Servico;
import br.com.oficina.domain.servico.ServicoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para listagem de serviços")
class ListarServicosUseCaseTest {

    @Mock
    private ServicoRepository repository;

    @InjectMocks
    private ListarServicosUseCase useCase;

    @Test
    @DisplayName("Deve listar todos os serviços")
    void deveListarTodosOsServicos() {
        List<Servico> servicos = List.of(
                new Servico("Troca de Óleo", "Descrição qualquer", new BigDecimal("80.00"), 60),
                new Servico("Alinhamento", "Descrição qualquer", new BigDecimal("120.00"), 90));

        when(repository.listarTodos()).thenReturn(servicos);

        List<ServicoResponse> response = useCase.executar();

        assertEquals(2, response.size());
        verify(repository, times(1)).listarTodos();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há serviços")
    void deveRetornarListaVaziaQuandoNaoHaServicos() {
        when(repository.listarTodos()).thenReturn(List.of());

        List<ServicoResponse> response = useCase.executar();

        assertTrue(response.isEmpty());
    }
}