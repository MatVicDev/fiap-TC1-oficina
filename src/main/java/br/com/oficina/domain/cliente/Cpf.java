package br.com.oficina.domain.cliente;

public class Cpf {
    private final String numero;

    public Cpf(String numero) {
        if (!isValid(numero)) {
            throw new IllegalArgumentException("CPF inválido");
        }

        this.numero = numero.replaceAll("\\D", "");
    }

    private boolean isValid(String cpf) {
        return cpf != null && cpf.replaceAll("\\D", "").length()  == 11;
    }
}
