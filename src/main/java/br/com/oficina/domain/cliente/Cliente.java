package br.com.oficina.domain.cliente;

import java.time.LocalDateTime;
import java.util.UUID;

public class Cliente {
    private UUID id;
    private String nome;
    private Cpf cpf;
    private String telefone;
    private String email;
    private LocalDateTime dataCadastro;

    public Cliente(String nome, String cpfRaw, String telefone, String email) {
        validarNome(nome);
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.cpf = new Cpf(cpfRaw);
        this.telefone = telefone;
        this.email = email;
        this.dataCadastro = LocalDateTime.now();
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().length() < 3) {
            throw new IllegalArgumentException("O nome do cliente deve ter pelo menos 3 caracteres.");
        }
    }

    public void atualizarContatos(String novoTelefone, String novoEmail) {
        if (novoTelefone != null && !novoTelefone.isBlank()) {
            this.telefone = novoTelefone;
        }

        if (novoEmail != null && !novoEmail.isBlank()) {
            this.email = novoEmail;
        }
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public String getCpfFormatado() {
        return cpf.getNumero();
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }
}