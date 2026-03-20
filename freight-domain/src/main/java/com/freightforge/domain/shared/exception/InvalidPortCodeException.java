package com.freightforge.domain.shared.exception;

public class InvalidPortCodeException extends FreightDomainException {

    public InvalidPortCodeException(String code) {
        super("Invalid port code: '" + code + "'. Must be a 5-character UN/LOCODE (e.g. INBLR, SGSIN)");
    }
}