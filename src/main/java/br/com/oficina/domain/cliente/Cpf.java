package br.com.oficina.domain.cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Cpf {
    @Column(name = "cpf", length = 14, nullable = false, unique = true)
    private String numero;

    protected Cpf() {}

    public Cpf(String numero) {
        if (!isValid(numero)) {
            throw new IllegalArgumentException("CPF/CNPJ inválido: " + numero);
        }

        this.numero = sanitize(numero);
    }

    private String sanitize(String valor) {
        return valor.replaceAll("\\D", "");
    }

    private boolean isValid(String valor) {
        if (valor == null) return false;
        String sanitized = sanitize(valor);
        if (sanitized.length() == 11) return isValidCpf(sanitized);
        if (sanitized.length() == 14) return isValidCnpj(sanitized);
        return false;
    }

    private boolean isValidCpf(String cpf) {
        if (cpf.chars().distinct().count() == 1) return false;

        int sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * (10 - i);
        }

        int first = 11 - (sum % 11);
        if (first >= 10) first = 0;
        if (first != (cpf.charAt(9) - '0')) return false;

        sum = 0;
        for (int i = 0; i < 10; i++)
            sum += (cpf.charAt(i) - '0') * (11 - i);
        int second = 11 - (sum % 11);
        if (second >= 10) second = 0;
        return second == (cpf.charAt(10) - '0');
    }

    private boolean isValidCnpj(String cnpj) {
        if (cnpj.chars().distinct().count() == 1) {
            return false;
        }

        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            sum += (cnpj.charAt(i) - '0') * weights1[i];
        }

        int first = sum % 11 < 2 ? 0 : 11 - (sum % 11);
        if (first != (cnpj.charAt(12) - '0')) return false;

        sum = 0;
        for (int i = 0; i < 13; i++)
            sum += (cnpj.charAt(i) - '0') * weights2[i];
        int second = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        return second == (cnpj.charAt(13) - '0');
    }

    public String getNumero() {
        return numero;
    }
}
