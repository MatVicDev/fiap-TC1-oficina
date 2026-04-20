package br.com.oficina.domain.veiculo;

public class Placa {
    private final String numero;

    public Placa(String numero) {
        if (!isValid(numero)) {
            throw new IllegalArgumentException("Placa inválida. Use o padrão AAA0000 ou ABC1D23.");
        }

        this.numero = numero.toUpperCase();
    }

    private boolean isValid(String numero) {
        return numero != null && !numero.toUpperCase().matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}");
    }

    public String getNumero() {
        return numero;
    }
}
