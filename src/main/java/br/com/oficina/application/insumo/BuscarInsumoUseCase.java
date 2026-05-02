package br.com.oficina.application.insumo;

import br.com.oficina.api.insumo.dto.InsumoResponse;
import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.estoque.EstoqueRepository;
import br.com.oficina.domain.exception.EntidadeNaoEncontradaException;
import br.com.oficina.domain.insumo.Insumo;
import br.com.oficina.domain.insumo.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuscarInsumoUseCase {
    private final InsumoRepository repository;
    private final EstoqueRepository estoqueRepository;

    public InsumoResponse executar(UUID id) {
        Insumo insumo = repository.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Insumo não encontrado"));

        Estoque estoque = estoqueRepository.buscarPorInsumoId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Insumo não encontrado no estoque"));

        return InsumoMapper.toResponse(insumo, estoque);
    }
}
