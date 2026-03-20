package com.freightforge.domain.shared;

import java.time.Instant;

public class AuditInfo {

    private final Instant createdAt;
    private Instant updatedAt;
    private final String createdBy;
    private String updatedBy;

    public AuditInfo(String createdBy) {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.createdBy = createdBy;
        this.updatedBy = createdBy;
    }

    // Called whenever the entity changes
    public void markUpdated(String updatedBy) {
        this.updatedAt = Instant.now();
        this.updatedBy = updatedBy;
    }

    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public String getUpdatedBy() { return updatedBy; }
}