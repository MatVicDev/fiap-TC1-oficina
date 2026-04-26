package br.com.oficina.domain.veiculo;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "veiculos")
public class Veiculo {

    @Id
    private UUID id;

    @Embedded
    private Placa placa;

    private String marca;
    private String modelo;
    private Integer ano;
    private String cor;

    @Column(nullable = false)
    private String cpfProprietario;

    public Veiculo(Placa placa, String marca, String modelo, Integer ano, String cor, String cpfProprietario) {
        validarAno(ano);
        this.id = UUID.randomUUID();
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.cpfProprietario = cpfProprietario;
    }

    private void validarAno(Integer ano) {
        int anoAtual = java.time.Year.now().getValue();
        if (ano < 1900 || ano > anoAtual + 1) {
            throw new IllegalArgumentException("Ano do veículo inválido.");
        }
    }

    public UUID getId() {
        return id;
    }

    public Placa getPlaca() {
        return placa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Integer getAno() {
        return ano;
    }

    public String getCor() {
        return cor;
    }

    public String getCpfProprietario() {
        return cpfProprietario;
    }
}