package br.com.oficina.api.exception;

public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
