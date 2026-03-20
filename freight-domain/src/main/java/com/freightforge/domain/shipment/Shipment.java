package com.freightforge.domain.shipment;

import com.freightforge.domain.cargo.Cargo;
import com.freightforge.domain.shared.AuditInfo;
import com.freightforge.domain.shared.Port;
import com.freightforge.domain.shared.exception.InvalidShipmentStateException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Shipment {

    private final UUID id;
    private final UUID bookingId;
    private UUID carrierId;
    private final String trackingNumber;
    private ShipmentStatus status;
    private final Port originPort;
    private final Port destinationPort;
    private Instant estimatedDeparture;
    private Instant estimatedArrival;
    private Instant actualDeparture;
    private Instant actualArrival;
    private final List<TrackingEvent> trackingEvents;
    private final List<Cargo> cargoList;
    private final AuditInfo auditInfo;

    // The only way to create a new Shipment
    public static Shipment create(UUID bookingId, Port origin, Port destination) {
        return new Shipment(
                UUID.randomUUID(),
                bookingId,
                generateTrackingNumber(),
                origin,
                destination
        );
    }

    private Shipment(UUID id, UUID bookingId, String trackingNumber,
                     Port origin, Port destination) {
        this.id = id;
        this.bookingId = bookingId;
        this.trackingNumber = trackingNumber;
        this.originPort = origin;
        this.destinationPort = destination;
        this.status = ShipmentStatus.PENDING;
        this.trackingEvents = new ArrayList<>();
        this.cargoList = new ArrayList<>();
        this.auditInfo = new AuditInfo("system");
    }

    // ── Business behavior methods ──────────────────────────────────

    public void assignCarrier(UUID carrierId, Instant etd, Instant eta) {
        if (this.status != ShipmentStatus.PENDING) {
            throw new InvalidShipmentStateException(status.name(), "assign carrier");
        }
        this.carrierId = carrierId;
        this.estimatedDeparture = etd;
        this.estimatedArrival = eta;
        this.status = ShipmentStatus.CONFIRMED;
        addEvent(TrackingEventType.BOOKING_CONFIRMED,
                originPort.code(), "Carrier assigned, shipment confirmed");
    }

    public void depart() {
        if (this.status != ShipmentStatus.CONFIRMED) {
            throw new InvalidShipmentStateException(status.name(), "depart");
        }
        this.status = ShipmentStatus.IN_TRANSIT;
        this.actualDeparture = Instant.now();
        addEvent(TrackingEventType.DEPARTED_ORIGIN,
                originPort.code(), "Shipment departed origin port");
    }

    public void arrive() {
        if (this.status != ShipmentStatus.IN_TRANSIT) {
            throw new InvalidShipmentStateException(status.name(), "arrive");
        }
        this.status = ShipmentStatus.ARRIVED;
        this.actualArrival = Instant.now();
        addEvent(TrackingEventType.ARRIVED_DESTINATION,
                destinationPort.code(), "Shipment arrived at destination port");
    }

    public void placeOnCustomsHold(String reason) {
        if (this.status != ShipmentStatus.ARRIVED) {
            throw new InvalidShipmentStateException(status.name(), "customs hold");
        }
        this.status = ShipmentStatus.CUSTOMS_HOLD;
        addEvent(TrackingEventType.EXCEPTION,
                destinationPort.code(), "Customs hold: " + reason);
    }

    public void deliver() {
        if (this.status != ShipmentStatus.ARRIVED
                && this.status != ShipmentStatus.CUSTOMS_HOLD) {
            throw new InvalidShipmentStateException(status.name(), "deliver");
        }
        this.status = ShipmentStatus.DELIVERED;
        addEvent(TrackingEventType.DELIVERED,
                destinationPort.code(), "Shipment delivered to consignee");
    }

    public void cancel() {
        if (this.status.isTerminal()) {
            throw new InvalidShipmentStateException(status.name(), "cancel");
        }
        this.status = ShipmentStatus.CANCELLED;
    }

    public void addCargo(Cargo cargo) {
        if (this.status != ShipmentStatus.PENDING) {
            throw new InvalidShipmentStateException(status.name(), "add cargo");
        }
        this.cargoList.add(cargo);
    }

    // ── Private helpers ────────────────────────────────────────────

    private void addEvent(TrackingEventType type, String location, String description) {
        trackingEvents.add(TrackingEvent.of(this.id, type, location, description));
    }

    private static String generateTrackingNumber() {
        return "FF-" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                .toUpperCase();
    }

    // ── Getters — defensive copies for collections ─────────────────

    public UUID getId() { return id; }
    public UUID getBookingId() { return bookingId; }
    public UUID getCarrierId() { return carrierId; }
    public String getTrackingNumber() { return trackingNumber; }
    public ShipmentStatus getStatus() { return status; }
    public Port getOriginPort() { return originPort; }
    public Port getDestinationPort() { return destinationPort; }
    public Instant getEstimatedDeparture() { return estimatedDeparture; }
    public Instant getEstimatedArrival() { return estimatedArrival; }
    public Instant getActualDeparture() { return actualDeparture; }
    public Instant getActualArrival() { return actualArrival; }
    public AuditInfo getAuditInfo() { return auditInfo; }

    // Return unmodifiable copies — nobody can add to the list directly
    public List<TrackingEvent> getTrackingEvents() {
        return Collections.unmodifiableList(trackingEvents);
    }
    public List<Cargo> getCargoList() {
        return Collections.unmodifiableList(cargoList);
    }
}