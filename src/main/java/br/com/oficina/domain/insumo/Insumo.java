package br.com.oficina.domain.insumo;

import br.com.oficina.exception.DomainException;

import java.math.BigDecimal;

public class Insumo {
    private Long id;
    private String descricao;
    private BigDecimal precoBase;
    private TipoInsumo tipo;
    private Integer saldoEstoque;

    public Insumo(String descricao, BigDecimal precoBase, TipoInsumo tipo, Integer saldoInicial) {
        if (precoBase.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("Preço não pode ser negativo.");
        }

        this.descricao = descricao;
        this.precoBase = precoBase;
        this.tipo = tipo;
        this.saldoEstoque = (tipo == TipoInsumo.PECA) ? saldoInicial : 0;
    }

    public void deduzirEstoque(Integer quantidade) {
        if (this.tipo == TipoInsumo.SERVICO) {
            return;
        }

        if (this.saldoEstoque < quantidade) {
            throw new DomainException("Estoque insuficiente para o insumo: " + descricao);
        }

        this.saldoEstoque -= quantidade;
    }

    public void reporEstoque(Integer quantidade) {
        if (this.tipo == TipoInsumo.PECA) {
            this.saldoEstoque += quantidade;
        }
    }

    public Long getId() {
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

    public Integer getSaldoEstoque() {
        return saldoEstoque;
    }
}
