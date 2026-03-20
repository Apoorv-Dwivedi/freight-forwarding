package com.freightforge.domain.shared.exception;

public class InvalidShipmentStateException extends FreightDomainException {

    public InvalidShipmentStateException(String currentStatus, String attemptedAction) {
        super("Cannot perform '" + attemptedAction + "' on shipment in status: " + currentStatus);
    }
}