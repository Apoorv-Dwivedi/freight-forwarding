package com.freightforge.domain.quote;

import com.freightforge.domain.shared.AuditInfo;
import com.freightforge.domain.shared.Money;
import com.freightforge.domain.shared.Port;
import com.freightforge.domain.shared.exception.QuoteExpiredException;
import java.time.Instant;
import java.util.UUID;

public class Quote {

    private final UUID id;
    private final UUID customerId;
    private final Port originPort;
    private final Port destinationPort;
    private final Money totalAmount;
    private QuoteStatus status;
    private final Instant validUntil;
    private final AuditInfo auditInfo;

    public static Quote create(UUID customerId, Port origin,
                               Port destination, Money amount,
                               Instant validUntil) {
        if (validUntil.isBefore(Instant.now())) {
            throw new IllegalArgumentException("Quote validity date must be in the future");
        }
        return new Quote(UUID.randomUUID(), customerId, origin,
                destination, amount, validUntil);
    }

    private Quote(UUID id, UUID customerId, Port originPort,
                  Port destinationPort, Money totalAmount, Instant validUntil) {
        this.id = id;
        this.customerId = customerId;
        this.originPort = originPort;
        this.destinationPort = destinationPort;
        this.totalAmount = totalAmount;
        this.status = QuoteStatus.DRAFT;
        this.validUntil = validUntil;
        this.auditInfo = new AuditInfo("system");
    }

    // Send to customer
    public void send() {
        if (this.status != QuoteStatus.DRAFT) {
            throw new IllegalStateException("Only DRAFT quotes can be sent");
        }
        this.status = QuoteStatus.SENT;
    }

    // Customer accepts — returns true so use case knows to create a Booking
    public void accept() {
        if (this.status != QuoteStatus.SENT) {
            throw new IllegalStateException("Only SENT quotes can be accepted");
        }
        if (Instant.now().isAfter(this.validUntil)) {
            throw new QuoteExpiredException(this.id.toString());
        }
        this.status = QuoteStatus.ACCEPTED;
    }

    public void reject() {
        if (this.status != QuoteStatus.SENT) {
            throw new IllegalStateException("Only SENT quotes can be rejected");
        }
        this.status = QuoteStatus.REJECTED;
    }

    public void expire() {
        if (this.status == QuoteStatus.SENT) {
            this.status = QuoteStatus.EXPIRED;
        }
    }

    public boolean isExpired() {
        return Instant.now().isAfter(this.validUntil);
    }

    public UUID getId() { return id; }
    public UUID getCustomerId() { return customerId; }
    public Port getOriginPort() { return originPort; }
    public Port getDestinationPort() { return destinationPort; }
    public Money getTotalAmount() { return totalAmount; }
    public QuoteStatus getStatus() { return status; }
    public Instant getValidUntil() { return validUntil; }
    public AuditInfo getAuditInfo() { return auditInfo; }
}