package br.com.oficina.application.ordemServico;

import br.com.oficina.domain.cliente.ClienteRepository;
import br.com.oficina.domain.notificacao.NotificacaoPort;
import br.com.oficina.domain.ordemServico.OrdemServico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificarStatusOrdemServicoService {
    private final ClienteRepository clienteRepository;
    private final NotificacaoPort notificacaoPort;

    public void notificarMudancaStatus(OrdemServico os) {
        clienteRepository.buscarPorId(os.getClienteId())
                .filter(cliente -> cliente.getEmail() != null && !cliente.getEmail().isBlank())
                .ifPresent(cliente -> notificacaoPort.notificar(
                        cliente.getEmail(),
                        "Atualização da sua Ordem de Serviço",
                        "Sua OS " + os.getId() + " agora está: " + os.getStatus()));
    }
}
