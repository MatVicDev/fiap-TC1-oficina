package br.com.oficina.domain.ordemServico;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "itens_ordem_servico", indexes = {
        @Index(name = "idx_itens_os_ordem_servico_id", columnList = "ordem_servico_id"),
        @Index(name = "idx_itens_os_insumo_id", columnList = "insumoId")
})
public class ItemOS {

    @Id
    private UUID id;

    private UUID insumoId;

    @ManyToOne
    @JoinColumn(name = "ordem_servico_id")
    private OrdemServico ordemServico;

    private String descricao;
    private BigDecimal precoUnitario;
    private Integer quantidade;

    public ItemOS(UUID insumoId, String descricao, BigDecimal precoUnitario, Integer quantidade, OrdemServico ordemServico) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        this.id = UUID.randomUUID();
        this.insumoId = insumoId;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.quantidade = quantidade;
        this.ordemServico = ordemServico;
    }

    public BigDecimal getPrecoTotal() {
        return this.precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }

    public UUID getId() {
        return id;
    }

    public UUID getInsumoId() {
        return insumoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}
