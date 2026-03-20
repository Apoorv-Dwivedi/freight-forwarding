package com.freightforge.domain.invoice;

public enum InvoiceStatus {
    DRAFT,      // Being prepared
    ISSUED,     // Sent to customer
    PAID,       // Payment received
    OVERDUE,    // Past due date, not paid
    CANCELLED;  // Voided

    public boolean isPayable() {
        return this == ISSUED || this == OVERDUE;
    }
}