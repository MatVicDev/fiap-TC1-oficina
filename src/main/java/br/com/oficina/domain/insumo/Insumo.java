package br.com.oficina.domain.insumo;

import br.com.oficina.exception.DomainException;

import java.math.BigDecimal;
import java.util.UUID;

public class Insumo {
    private UUID id;
    private String descricao;
    private BigDecimal precoBase;
    private TipoInsumo tipo;
    private Integer quantidadeEstoque;

    public Insumo(String descricao, BigDecimal precoBase, TipoInsumo tipo, Integer quantidadeInicial) {
        if (precoBase.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("Preço não pode ser negativo.");
        }

        this.id = UUID.randomUUID();
        this.descricao = descricao;
        this.precoBase = precoBase;
        this.tipo = tipo;
        this.quantidadeEstoque = (tipo == TipoInsumo.PECA || tipo == TipoInsumo.PRODUTO)  ? quantidadeInicial : 0;
    }

    public void deduzirEstoque(Integer quantidade) {
        if (this.tipo == TipoInsumo.SERVICO) {
            return;
        }

        if (this.quantidadeEstoque < quantidade) {
            throw new DomainException("Estoque insuficiente para o insumo: " + descricao);
        }

        this.quantidadeEstoque -= quantidade;
    }

    public void reporEstoque(Integer quantidade) {
        if (this.tipo == TipoInsumo.PECA) {
            this.quantidadeEstoque += quantidade;
        }
    }

    public UUID getId() {
        return id;
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

    public Integer getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
}
