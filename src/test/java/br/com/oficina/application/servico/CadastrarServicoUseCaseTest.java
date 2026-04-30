package br.com.oficina.application.servico;

import br.com.oficina.api.servico.dto.CadastrarServicoRequest;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do CadastrarServicoUseCase")
class CadastrarServicoUseCaseTest {

    @Mock
    private ServicoRepository repository;

    @InjectMocks
    private CadastrarServicoUseCase useCase;

    @Test
    @DisplayName("Deve cadastrar serviço com sucesso")
    void deveCadastrarServicoComSucesso() {
        CadastrarServicoRequest request = new CadastrarServicoRequest(
                "Troca de Óleo",
                "Troca de óleo e filtro",
                new BigDecimal("80.00"),
                60);

        when(repository.salvar(any(Servico.class)))
                .thenAnswer(i -> i.getArgument(0));

        ServicoResponse reponse = useCase.executar(request);

        assertNotNull(reponse);
        assertEquals("Troca de Óleo", reponse.nome());
        assertEquals(60, reponse.tempoPrevisto());
        verify(repository, times(1)).salvar(any(Servico.class));
    }

    @Test
    @DisplayName("Deve lançar exceção para valor negativo")
    void deveLancarExcecaoParaValorNegativo() {
        CadastrarServicoRequest request = new CadastrarServicoRequest(
                "Troca de Óleo",
                "Troca de óleo e filtro",
                new BigDecimal("-80.00"),
                60);

        assertThrows(Exception.class, () -> useCase.executar(request));
        verify(repository, never()).salvar(any());
    }
}