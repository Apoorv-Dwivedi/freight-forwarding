package com.freightforge.domain.carrier;

import java.util.UUID;

public class Carrier {

    private final UUID id;
    private final String name;
    private final String carrierCode;  // e.g. "MAEU" for Maersk, "MSCU" for MSC
    private final CarrierType type;
    private boolean active;

    public static Carrier create(String name, String carrierCode, CarrierType type) {
        if (carrierCode == null || carrierCode.isBlank()) {
            throw new IllegalArgumentException("Carrier code is required");
        }
        return new Carrier(UUID.randomUUID(), name, carrierCode.toUpperCase(), type);
    }

    private Carrier(UUID id, String name, String carrierCode, CarrierType type) {
        this.id = id;
        this.name = name;
        this.carrierCode = carrierCode;
        this.type = type;
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getCarrierCode() { return carrierCode; }
    public CarrierType getType() { return type; }
    public boolean isActive() { return active; }
}