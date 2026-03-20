package com.freightforge.domain.booking;

public enum BookingStatus {
    PENDING,      // Submitted, awaiting ops confirmation
    CONFIRMED,    // Ops confirmed, shipment will be created
    CANCELLED;    // Cancelled by customer or ops

    public boolean isCancellable() {
        return this == PENDING;
    }
}