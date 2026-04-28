package br.com.oficina.application.insumo;

import br.com.oficina.api.insumo.dto.InsumoResponse;
import br.com.oficina.domain.estoque.Estoque;
import br.com.oficina.domain.insumo.Insumo;

public class InsumoMapper {
    public static InsumoResponse toResponse(Insumo insumo, Estoque estoque) {
        return new InsumoResponse(
                insumo.getId(),
                insumo.getNome(),
                insumo.getDescricao(),
                insumo.getPrecoBase(),
                insumo.getTipo(),
                estoque.getQuantidade());
    }
}
