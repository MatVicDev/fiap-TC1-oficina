package br.com.oficina.domain.veiculo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Placa {
    @Column(name = "placa", length = 8, nullable = false, unique = true)
    private String numero;

    protected Placa() {}

    public Placa(String numero) {
        if (!isValid(numero)) {
            throw new IllegalArgumentException(
                    "Placa inválida. Use o padrão AAA0000 ou ABC1D23.");
        }
        this.numero = numero.toUpperCase().replaceAll("[^A-Z0-9]", "");
    }

    private boolean isValid(String numero) {
        if (numero == null) return false;
        String sanitized = numero.toUpperCase().replaceAll("[^A-Z0-9]", "");
        boolean antigoValido = sanitized.matches("[A-Z]{3}[0-9]{4}");
        boolean mercosulValido = sanitized.matches("[A-Z]{3}[0-9][A-Z][0-9]{2}");

        return antigoValido || mercosulValido;
    }

    public String getNumero() {
        return numero;
    }
}
