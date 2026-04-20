package br.com.oficina.domain.insumo;

import java.math.BigDecimal;

public class Insumo {
    private Long  id;
    private String descricao;
    private TipoInsumo tipoInsumo;
    private BigDecimal precoBase;
    private Integer saldoEstoque;

    public void deduzirEstoque(Integer quantidade) {

    }

    public void reporEstoque(Integer quantidade) {

    }
}
