package br.com.oficina.domain.insumo;

import br.com.oficina.exception.DomainException;

import java.math.BigDecimal;
import java.util.UUID;

public class Insumo {
    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal precoBase;
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
