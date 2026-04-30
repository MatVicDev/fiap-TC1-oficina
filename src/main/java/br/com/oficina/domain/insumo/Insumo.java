package br.com.oficina.domain.insumo;

import br.com.oficina.api.exception.DomainException;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "insumos")
public class Insumo {

    @Id
    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal precoBase;

    @Enumerated(EnumType.STRING)
    private TipoInsumo tipo;

    public Insumo(String nome, String descricao, BigDecimal precoBase, TipoInsumo tipo) {
        if (precoBase.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("Preço não pode ser negativo.");
        }

        this.id = UUID.randomUUID();
        this.nome = nome;
        this.descricao = descricao;
        this.precoBase = precoBase;
        this.tipo = tipo;
    }

    public void atualizar(String nome, String descricao, BigDecimal precoBase, TipoInsumo tipo) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }

        if (descricao != null && !descricao.isBlank()) {
            this.descricao = descricao;
        }

        if (precoBase != null && precoBase.compareTo(BigDecimal.ZERO) > 0) {
            this.precoBase = precoBase;
        }

        if (tipo != null && this.tipo != tipo) {
            this.tipo = tipo;
        }
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPrecoBase() {
        return precoBase;
    }

    public TipoInsumo getTipo() {
        return tipo;
    }
}
