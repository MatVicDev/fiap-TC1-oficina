package br.com.oficina.domain.ordemServico;

import java.math.BigDecimal;
import java.util.UUID;

public class ItemOS {
    private UUID id;
    private Long insumoId;
    private String descricao;
    private BigDecimal precoUnitario;
    private Integer quantidade;

    public ItemOS(Long insumoId, String descricao, BigDecimal precoUnitario, Integer quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        this.id = UUID.randomUUID();
        this.insumoId = insumoId;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoTotal() {
        return this.precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
