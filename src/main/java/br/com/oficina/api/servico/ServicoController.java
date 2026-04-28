package br.com.oficina.api.servico;

import br.com.oficina.api.servico.dto.AtualizarServicoRequest;
import br.com.oficina.api.servico.dto.CadastrarServicoRequest;
import br.com.oficina.api.servico.dto.ServicoResponse;
import br.com.oficina.application.servico.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServicoController {
    private final CadastrarServicoUseCase cadastrarServicoUseCase;
    private final ListarServicosUseCase listarServicosUseCase;
    private final BuscarServicoUseCase buscarServicoUseCase;
    private final AtualizarServicoUseCase atualizarServicoUseCase;
    private final ExcluirServicoUseCase excluirServicoUseCase;

    @PostMapping
    public ResponseEntity<ServicoResponse> cadastrar(@Valid @RequestBody CadastrarServicoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastrarServicoUseCase.executar(request));
    }

    @GetMapping
    public ResponseEntity<List<ServicoResponse>> listar() {
        return ResponseEntity.ok(listarServicosUseCase.executar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicoResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(buscarServicoUseCase.executar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicoResponse> atualizar(@PathVariable UUID id, @RequestBody AtualizarServicoRequest request) {
        return ResponseEntity.ok(atualizarServicoUseCase.executar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        excluirServicoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }
}
