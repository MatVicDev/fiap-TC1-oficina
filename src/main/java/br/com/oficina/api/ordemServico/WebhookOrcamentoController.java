package br.com.oficina.api.ordemServico;

import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import br.com.oficina.api.ordemServico.dto.WebhookOrcamentoDecisaoRequest;
import br.com.oficina.application.ordemServico.ProcessarNotificacaoOrcamentoUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/webhooks/ordens-servico")
@RequiredArgsConstructor
public class WebhookOrcamentoController {
    private final ProcessarNotificacaoOrcamentoUseCase processarNotificacaoOrcamentoUseCase;

    @SecurityRequirements
    @PostMapping("/{id}/orcamento")
    public ResponseEntity<OrdemServicoResponse> receberDecisao(
            @PathVariable UUID id, @Valid @RequestBody WebhookOrcamentoDecisaoRequest request) {
        return ResponseEntity.ok(processarNotificacaoOrcamentoUseCase.executar(id, request.decisao()));
    }
}
