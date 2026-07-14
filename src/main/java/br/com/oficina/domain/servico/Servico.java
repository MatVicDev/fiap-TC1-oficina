package br.com.oficina.domain.servico;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "servicos")
public class Servico {

    @Id
    private UUID id;

    private String nome;
    private String descricao;
    private BigDecimal precoBase;
    private Integer tempoPrevisto;

    public Servico(String nome, String descricao, BigDecimal precoBase, Integer tempoPrevisto) {
        if (precoBase.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Não é permitido valores negativos");
        }

        this.id = UUID.randomUUID();
        this.nome = nome;
        this.descricao = descricao;
        this.precoBase = precoBase;
        this.tempoPrevisto = tempoPrevisto;
    }

    public void atualizar(String nome, String descricao, BigDecimal precoBase, Integer tempoPrevisto) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }

        if (descricao != null && !descricao.isBlank()) {
            this.descricao = descricao;
        }

        if (precoBase != null && precoBase.compareTo(BigDecimal.ZERO) > 0) {
            this.precoBase = precoBase;
        }

        if (tempoPrevisto != null && tempoPrevisto > 0) {
            this.tempoPrevisto = tempoPrevisto;
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

    public Integer getTempoPrevisto() {
        return tempoPrevisto;
    }
}
