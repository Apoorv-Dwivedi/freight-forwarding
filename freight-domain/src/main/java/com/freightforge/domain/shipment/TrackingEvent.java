package com.freightforge.domain.shipment;

import com.freightforge.domain.shared.Port;
import java.time.Instant;
import java.util.UUID;

public class TrackingEvent {

    private final UUID id;
    private final UUID shipmentId;
    private final TrackingEventType eventType;
    private final String location;
    private final Instant eventTimestamp;
    private final String description;

    // Package-private — only Shipment can create TrackingEvents
    static TrackingEvent of(UUID shipmentId, TrackingEventType type,
                            String location, String description) {
        return new TrackingEvent(UUID.randomUUID(), shipmentId,
                type, location, Instant.now(), description);
    }

    private TrackingEvent(UUID id, UUID shipmentId, TrackingEventType eventType,
                          String location, Instant eventTimestamp, String description) {
        this.id = id;
        this.shipmentId = shipmentId;
        this.eventType = eventType;
        this.location = location;
        this.eventTimestamp = eventTimestamp;
        this.description = description;
    }

    public UUID getId() { return id; }
    public UUID getShipmentId() { return shipmentId; }
    public TrackingEventType getEventType() { return eventType; }
    public String getLocation() { return location; }
    public Instant getEventTimestamp() { return eventTimestamp; }
    public String getDescription() { return description; }
}