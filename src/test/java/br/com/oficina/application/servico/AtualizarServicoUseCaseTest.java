package br.com.oficina.application.servico;

import br.com.oficina.api.servico.dto.AtualizarServicoRequest;
import br.com.oficina.api.servico.dto.ServicoResponse;
import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do AtualizarServicoUseCase")
class AtualizarServicoUseCaseTest {

    @Mock
    private ServicoRepository repository;

    @InjectMocks
    private AtualizarServicoUseCase useCase;

    @Test
    @DisplayName("Deve atualizar serviço com sucesso")
    void deveAtualizarServicoComSucesso() {
        Servico servico = new Servico(
                "Troca de Óleo",
                "Descrição",
                new BigDecimal("80.00"),
                60);

        AtualizarServicoRequest request = new AtualizarServicoRequest(
                "Troca de Óleo Premium",
                "Nova descrição",
                new BigDecimal("100.00"),
                45);

        when(repository.buscarPorId(any())).thenReturn(Optional.of(servico));
        when(repository.salvar(any())).thenAnswer(i -> i.getArgument(0));

        ServicoResponse response = useCase.executar(UUID.randomUUID(), request);

        assertNotNull(response);
        assertEquals("Troca de Óleo Premium", response.nome());
        verify(repository, times(1)).salvar(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando serviço não for encontrado")
    void deveLancarExcecaoQuandoServicoNaoEncontrado() {
        when(repository.buscarPorId(any())).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> useCase.executar(UUID.randomUUID(),
                        new AtualizarServicoRequest("Nome", "Desc",
                                new BigDecimal("80.00"), 60)));
    }
}