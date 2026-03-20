package com.freightforge.domain.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public record Money(BigDecimal amount, String currencyCode) {

    public Money {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (currencyCode == null || currencyCode.isBlank()) {
            throw new IllegalArgumentException("Currency code cannot be empty");
        }
        // Validate it's a real ISO 4217 currency code (USD, EUR, INR, etc.)
        try {
            Currency.getInstance(currencyCode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid currency code: " + currencyCode);
        }
        // Always store with 2 decimal places — $100 not $100.0000
        amount = amount.setScale(2, RoundingMode.HALF_UP);
        currencyCode = currencyCode.toUpperCase();
    }

    // Factory methods for convenience
    public static Money of(double amount, String currency) {
        return new Money(BigDecimal.valueOf(amount), currency);
    }

    public static Money ofUSD(double amount) {
        return new Money(BigDecimal.valueOf(amount), "USD");
    }

    public static Money zero(String currencyCode) {
        return new Money(BigDecimal.ZERO, currencyCode);
    }

    // Add two Money values — only if same currency
    public Money add(Money other) {
        if (!this.currencyCode.equals(other.currencyCode)) {
            throw new IllegalArgumentException(
                    "Cannot add different currencies: " + this.currencyCode + " and " + other.currencyCode
            );
        }
        return new Money(this.amount.add(other.amount), this.currencyCode);
    }

    // Check if this amount is greater than another
    public boolean isGreaterThan(Money other) {
        if (!this.currencyCode.equals(other.currencyCode)) {
            throw new IllegalArgumentException("Cannot compare different currencies");
        }
        return this.amount.compareTo(other.amount) > 0;
    }

    @Override
    public String toString() {
        return currencyCode + " " + amount.toPlainString();
    }
}