package br.com.oficina.exception;

public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
