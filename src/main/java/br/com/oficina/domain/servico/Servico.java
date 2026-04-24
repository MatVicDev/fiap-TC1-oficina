package br.com.oficina.domain.servico;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Servico {
    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private LocalDateTime tempoPrevisto;

    public Servico(String nome, String descricao, BigDecimal valor, LocalDateTime tempoPrevisto) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.tempoPrevisto = tempoPrevisto;
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

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDateTime getTempoPrevisto() {
        return tempoPrevisto;
    }
}
