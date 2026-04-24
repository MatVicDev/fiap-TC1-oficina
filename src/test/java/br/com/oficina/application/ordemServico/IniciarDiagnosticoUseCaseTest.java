package br.com.oficina.application.ordemServico;

import br.com.oficina.domain.ordemServico.OrdemServicoRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IniciarDiagnosticoUseCaseTest {

    @Mock
    private OrdemServicoRepository ordemServicoRepository;

}