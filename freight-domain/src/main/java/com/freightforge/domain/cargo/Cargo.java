package com.freightforge.domain.cargo;

import com.freightforge.domain.shared.Volume;
import com.freightforge.domain.shared.Weight;
import java.util.UUID;

public class Cargo {

    private final UUID id;
    private final UUID shipmentId;
    private final String description;
    private final Weight weight;
    private final Volume volume;
    private final CargoType type;
    private final String hsCode;          // Harmonized System code for customs
    private final boolean dangerousGoods;

    public static Cargo create(UUID shipmentId, String description,
                               Weight weight, Volume volume,
                               CargoType type, String hsCode) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Cargo description is required");
        }
        boolean isDangerous = (type == CargoType.HAZARDOUS);
        return new Cargo(UUID.randomUUID(), shipmentId, description,
                weight, volume, type, hsCode, isDangerous);
    }

    private Cargo(UUID id, UUID shipmentId, String description,
                  Weight weight, Volume volume, CargoType type,
                  String hsCode, boolean dangerousGoods) {
        this.id = id;
        this.shipmentId = shipmentId;
        this.description = description;
        this.weight = weight;
        this.volume = volume;
        this.type = type;
        this.hsCode = hsCode;
        this.dangerousGoods = dangerousGoods;
    }

    public UUID getId() { return id; }
    public UUID getShipmentId() { return shipmentId; }
    public String getDescription() { return description; }
    public Weight getWeight() { return weight; }
    public Volume getVolume() { return volume; }
    public CargoType getType() { return type; }
    public String getHsCode() { return hsCode; }
    public boolean isDangerousGoods() { return dangerousGoods; }
}