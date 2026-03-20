package com.freightforge.domain.shared.exception;

// All domain exceptions extend this
// RuntimeException means you don't have to declare "throws" everywhere
public class FreightDomainException extends RuntimeException {

    public FreightDomainException(String message) {
        super(message);
    }

    public FreightDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}