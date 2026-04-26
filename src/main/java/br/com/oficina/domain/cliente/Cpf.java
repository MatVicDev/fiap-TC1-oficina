package br.com.oficina.domain.cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Cpf {
    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String numero;

    public Cpf(String numero) {
        if (!isValid(numero)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        this.numero = numero.replaceAll("\\D", "");
    }

    private boolean isValid(String cpf) {
        return cpf != null && cpf.replaceAll("\\D", "").length()  == 11;
    }

    public String getNumero() {
        return numero;
    }
}
