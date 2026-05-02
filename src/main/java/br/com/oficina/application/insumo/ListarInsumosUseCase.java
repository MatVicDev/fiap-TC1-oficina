package br.com.oficina.application.insumo;

import br.com.oficina.api.insumo.dto.InsumoResponse;
import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
import br.com.oficina.domain.insumo.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarInsumosUseCase {
    private final InsumoRepository insumoRepository;
    private final EstoqueRepository estoqueRepository;

    public List<InsumoResponse> executar() {
        return insumoRepository.listarTodos().stream()
                .map(insumo -> {
                    Estoque estoque = estoqueRepository.buscarPorInsumoId(insumo.getId())
                            .orElseThrow(() -> new EntidadeNaoEncontradaException("Estoque não encontrado"));

                    return InsumoMapper.toResponse(insumo, estoque);
                }).toList();
    }
}
