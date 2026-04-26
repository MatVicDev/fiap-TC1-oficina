package br.com.oficina.domain.veiculo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Placa {
    @Column(name = "placa", nullable = false, length = 8, unique = true)
    private String numero;

    public Placa(String numero) {
        if (!isValid(numero)) {
            throw new IllegalArgumentException("Placa inválida. Use o padrão AAA0000 ou ABC1D23.");
        }

        this.numero = numero.toUpperCase();
    }

    private boolean isValid(String numero) {
        return numero != null && numero.toUpperCase().matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}");
    }

    public String getNumero() {
        return numero;
    }
}
