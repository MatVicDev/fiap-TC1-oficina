package br.com.oficina.domain.cliente;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Testes do Value Object Cpf")
class CpfTest {

    @Test
    @DisplayName("Deve criar CPF válido")
    void deveCriarCpfValido() {
        Cpf cpf = new Cpf("12345678901");
        assertEquals("12345678901", cpf.getNumero());
    }

    @Test
    @DisplayName("Deve aceitar CPF com formatação")
    void deveAceitarCpfComFormatacao() {
        Cpf cpf = new Cpf("123.456.789-01");
        assertEquals("12345678901", cpf.getNumero());
    }

    @Test
    @DisplayName("Deve lançar exceção para CPF nulo")
    void deveLancarExcecaoParaCpfNulo() {
        assertThrows(IllegalArgumentException.class, () -> new Cpf(null));
    }

    @Test
    @DisplayName("Deve lançar exceção para CPF com menos de 11 dígitos")
    void deveLancarExcecaoParaCpfCurto() {
        assertThrows(IllegalArgumentException.class, () -> new Cpf("1234567890"));
    }

    @Test
    @DisplayName("Deve lançar exceção para CPF com mais de 11 dígitos")
    void deveLancarExcecaoParaCpfLongo() {
        assertThrows(IllegalArgumentException.class, () -> new Cpf("123456789012"));
    }
}