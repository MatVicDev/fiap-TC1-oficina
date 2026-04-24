package br.com.oficina.domain.estoque;

import java.util.UUID;

public class Estoque {
    private UUID id;
    private UUID insumoId;
    private Integer quantidade;

    public Estoque(UUID insumoId, Integer quantidade) {
        this.id = UUID.randomUUID();
        this.insumoId = insumoId;
        this.quantidade = quantidade;
    }

    public void reduzir(Integer quantidade) {
        if (quantidade > this.quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente.");
        }

        this.quantidade -= quantidade;
    }

    public void repor(Integer quantidade) {
        this.quantidade += quantidade;
    }

    public UUID getId() {
        return id;
    }

    public UUID getInsumoId() {
        return insumoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}
