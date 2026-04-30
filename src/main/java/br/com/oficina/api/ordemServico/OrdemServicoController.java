package br.com.oficina.api.ordemServico;

import br.com.oficina.api.ordemServico.dto.CriarOSRequest;
import br.com.oficina.api.ordemServico.dto.OrdemServicoResponse;
import br.com.oficina.application.ordemServico.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ordens-servico")
@RequiredArgsConstructor
public class OrdemServicoController {
    private final CriarOSUseCase criarOSUseCase;
    private final ListarOSUseCase listarOSUseCase;
    private final BuscarOSUseCase buscarOSUseCase;
    private final IniciarDiagnosticoUseCase iniciarDiagnosticoUseCase;
    private final ConcluirDiagnosticoUseCase concluirDiagnosticoUseCase;
    private final AprovarOrcamentoUseCase aprovarOrcamentoUseCase;
    private final RejeitarOrcamentoUseCase rejeitarOrcamentoUseCase;
    private final ConcluirOrdemServicoUseCase concluirOrdemServicoUseCase;
    private final RegistrarEntregaUseCase registrarEntregaUseCase;
    private final AdicionarItemOSUseCase adicionarItemOSUseCase;

    @PostMapping
    public ResponseEntity<OrdemServicoResponse> criar(@Valid @RequestBody CriarOSRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarOSUseCase.executar(request));
    }

    @GetMapping
    public ResponseEntity<List<OrdemServicoResponse>> listar() {
        return ResponseEntity.ok(listarOSUseCase.executar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdemServicoResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(buscarOSUseCase.executar(id));
    }

    @PatchMapping("/{id}/diagnostico/iniciar")
    public ResponseEntity<OrdemServicoResponse> iniciarDiagnostico(@PathVariable UUID id) {
        return ResponseEntity.ok(iniciarDiagnosticoUseCase.executar(id));
    }

    @PatchMapping("/{id}/diagnostico/concluir")
    public ResponseEntity<OrdemServicoResponse> concluirDiagnostico(@PathVariable UUID id) {
        return ResponseEntity.ok(concluirDiagnosticoUseCase.executar(id));
    }

    @PatchMapping("/{id}/orcamento/aprovar")
    public ResponseEntity<OrdemServicoResponse> aprovarOrcamento(@PathVariable UUID id) {
        return ResponseEntity.ok(aprovarOrcamentoUseCase.executar(id));
    }

    @PatchMapping("/{id}/orcamento/rejeitar")
    public ResponseEntity<OrdemServicoResponse> rejeitarOrcamento(@PathVariable UUID id) {
        return ResponseEntity.ok(rejeitarOrcamentoUseCase.executar(id));
    }

    @PatchMapping("/{id}/servico/concluir")
    public ResponseEntity<OrdemServicoResponse> concluirServico(@PathVariable UUID id) {
        return ResponseEntity.ok(concluirOrdemServicoUseCase.executar(id));
    }

    @PatchMapping("/{id}/entrega")
    public ResponseEntity<OrdemServicoResponse> registrarEntrega(@PathVariable UUID id) {
        return ResponseEntity.ok(registrarEntregaUseCase.executar(id));
    }

    @PostMapping("/{id}/itens")
    public ResponseEntity<OrdemServicoResponse> adicionarItem(
            @PathVariable UUID id,
            @RequestParam UUID insumoId,
            @RequestParam Integer quantidade) {
        return ResponseEntity.ok(adicionarItemOSUseCase.executar(id, insumoId, quantidade));
    }
}
