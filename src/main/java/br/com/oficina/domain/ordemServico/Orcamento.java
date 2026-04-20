package br.com.oficina.domain.ordemServico;

import java.math.BigDecimal;

public record Orcamento(BigDecimal valorTotalPecas, BigDecimal valorTotalServicos) {

    public BigDecimal getValorTotalGeral() {
        return valorTotalPecas.add(valorTotalServicos);
    }
}