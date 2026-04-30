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
    private BigDecimal valor;
    private Integer tempoPrevisto;

    public Servico(String nome, String descricao, BigDecimal valor, Integer tempoPrevisto) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Não é permitido valores negativos");
        }

        this.id = UUID.randomUUID();
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.tempoPrevisto = tempoPrevisto;
    }

    public void atualizar(String nome, String descricao, BigDecimal valor, Integer tempoPrevisto) {
        if (nome != null && !nome.isBlank()) {
            this.nome = nome;
        }

        if (descricao != null && !descricao.isBlank()) {
            this.descricao = descricao;
        }

        if (valor != null && valor.compareTo(BigDecimal.ZERO) <= 0) {
            this.valor = valor;
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

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getTempoPrevisto() {
        return tempoPrevisto;
    }
}
