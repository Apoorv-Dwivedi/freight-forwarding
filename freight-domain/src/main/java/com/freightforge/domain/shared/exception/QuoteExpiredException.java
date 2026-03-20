package com.freightforge.domain.shared.exception;

public class QuoteExpiredException extends FreightDomainException {

    public QuoteExpiredException(String quoteId) {
        super("Quote " + quoteId + " has expired and can no longer be accepted");
    }
}