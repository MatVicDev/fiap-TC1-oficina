package br.com.oficina.application.servico;

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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para exclusão de serviços")
class ExcluirServicoUseCaseTest {

    @Mock
    private ServicoRepository repository;

    @InjectMocks
    private ExcluirServicoUseCase useCase;

    @Test
    @DisplayName("Deve excluir serviço com sucesso")
    void deveExcluirServicoComSucesso() {
        Servico servico = new Servico(
                "Troca de Óleo",
                "Descrição",
                new BigDecimal("80.00"),
                60);

        when(repository.buscarPorId(any())).thenReturn(Optional.of(servico));

        useCase.executar(UUID.randomUUID());

        verify(repository, times(1)).excluir(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando serviço não for encontrado")
    void deveLancarExcecaoQuandoServicoNaoEncontrado() {
        when(repository.buscarPorId(any())).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.executar(UUID.randomUUID()));
        verify(repository, never()).excluir(any());
    }
}