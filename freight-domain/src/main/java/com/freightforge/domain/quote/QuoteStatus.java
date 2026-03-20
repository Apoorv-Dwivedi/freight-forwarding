package com.freightforge.domain.quote;

public enum QuoteStatus {
    DRAFT,      // Being prepared by ops
    SENT,       // Sent to customer, awaiting response
    ACCEPTED,   // Customer accepted — will become a booking
    REJECTED,   // Customer rejected
    EXPIRED;    // valid_until date passed

    public boolean isActionable() {
        return this == SENT;
    }
}