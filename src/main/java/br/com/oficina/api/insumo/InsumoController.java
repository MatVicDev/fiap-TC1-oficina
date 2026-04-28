package br.com.oficina.api.insumo;

import br.com.oficina.api.insumo.dto.AtualizarEstoqueUseCase;
import br.com.oficina.api.insumo.dto.AtualizarInsumoRequest;
import br.com.oficina.api.insumo.dto.CadastrarInsumoRequest;
import br.com.oficina.api.insumo.dto.ListarInsumosUseCase;
import br.com.oficina.application.insumo.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/insumos")
@RequiredArgsConstructor
public class InsumoController {
    private final CadastrarInsumoUseCase cadastrarInsumoUseCase;
    private final ListarInsumosUseCase listarInsumosUseCase;
    private final BuscarInsumoUseCase buscarInsumoUseCase;
    private final AtualizarInsumoUseCase atualizarInsumoUseCase;
    private final ExcluirInsumoUseCase excluirInsumoUseCase;
    private final AtualizarEstoqueUseCase atualizarEstoqueUseCase;

    @PostMapping
    public ResponseEntity<InsumoResponse> cadastrar(@Valid @RequestBody CadastrarInsumoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastrarInsumoUseCase.executar(request));
    }

    @GetMapping
    public ResponseEntity<List<InsumoResponse>> listar() {
        return ResponseEntity.ok(listarInsumosUseCase.executar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsumoResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(buscarInsumoUseCase.executar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumoResponse> atualizar(@PathVariable UUID id, @RequestBody AtualizarInsumoRequest request) {
        return ResponseEntity.ok(atualizarInsumoUseCase.executar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        excluirInsumoUseCase.executar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estoque/repor")
    public ResponseEntity<Void> reporEstoque(@PathVariable UUID id, @RequestParam Integer quantidade) {
        atualizarEstoqueUseCase.repor(id, quantidade);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estoque/reduzir")
    public ResponseEntity<Void> reduzirEstoque(@PathVariable UUID id, @RequestParam Integer quantidade) {
        atualizarEstoqueUseCase.reduzir(id, quantidade);
        return ResponseEntity.noContent().build();
    }
}
