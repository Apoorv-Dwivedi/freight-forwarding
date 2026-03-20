package com.freightforge.domain.shipment;

public enum ShipmentStatus {

    PENDING,        // Created, not yet confirmed
    CONFIRMED,      // Carrier assigned, ready to move
    IN_TRANSIT,     // Departed origin port
    ARRIVED,        // Reached destination port
    CUSTOMS_HOLD,   // Held by customs authority
    DELIVERED,      // Handed to consignee
    CANCELLED;      // Cancelled before departure

    // Can this shipment still be modified?
    public boolean isActive() {
        return this == PENDING || this == CONFIRMED;
    }

    // Is this a terminal state — no further transitions possible?
    public boolean isTerminal() {
        return this == DELIVERED || this == CANCELLED;
    }
}