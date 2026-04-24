package br.com.oficina.domain.ordemServico;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "orcamentos")
public class Orcamento {

    @Id
    private UUID id;

    private BigDecimal valorTotal;

    @OneToOne(mappedBy = "orcamento")
    private OrdemServico ordemServico;

    @Enumerated(EnumType.STRING)
    private StatusOrcamento status;

    private LocalDateTime dataGeracao;

    public Orcamento(BigDecimal valorTotal, StatusOrcamento status) {
        this.id = UUID.randomUUID();
        this.valorTotal = valorTotal;
        this.status = status;
        this.dataGeracao = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public StatusOrcamento getStatus() {
        return status;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }
}