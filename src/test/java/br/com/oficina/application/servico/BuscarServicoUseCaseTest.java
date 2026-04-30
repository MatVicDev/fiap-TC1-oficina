package br.com.oficina.application.servico;

import br.com.oficina.api.exception.EntidadeNaoEncontradaException;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para busca de serviços")
class BuscarServicoUseCaseTest {

    @Mock
    private ServicoRepository repository;

    @InjectMocks
    private BuscarServicoUseCase useCase;

    @Test
    @DisplayName("Deve buscar serviço por ID com sucesso")
    void deveBuscarServicoPorIdComSucesso() {
        Servico servico = new Servico(
                "Troca de Óleo",
                "Descrição",
                new BigDecimal("80.00"),
                60);

        when(repository.buscarPorId(any())).thenReturn(Optional.of(servico));

        ServicoResponse response = useCase.executar(UUID.randomUUID());

        assertNotNull(response);
        assertEquals("Troca de Óleo", response.nome());
    }

    @Test
    @DisplayName("Deve lançar exceção quando serviço não for encontrado")
    void deveLancarExcecaoQuandoServicoNaoEncontrado() {
        when(repository.buscarPorId(any())).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.executar(UUID.randomUUID()));
    }
}