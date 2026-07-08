package br.com.oficina.domain.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> tratarViolacaoDeIntegridade(DataIntegrityViolationException ex) {
        String mensagem = ex.getMessage();

        if (mensagem != null && mensagem.contains("cpf")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("CPF/CNPJ já cadastrado no sistema.");
        }

        if (mensagem != null && mensagem.contains("placa")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Placa já cadastrada no sistema.");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Registro duplicado — dado já existe no sistema.");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> tratarEstadoInvalido(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarArgumentoInvalido(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<String> tratarExcecaoDeDominio(DomainException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<String> tratarNaoEncontrado(EntidadeNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> tratarErroGenerico(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno do servidor");
    }
}
