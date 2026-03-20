package com.freightforge.domain.customer;

import com.freightforge.domain.shared.AuditInfo;
import java.util.UUID;

public class Customer {

    private final UUID id;
    private String name;
    private String email;
    private String company;
    private CustomerType type;
    private final AuditInfo auditInfo;

    // Factory method — the only way to create a new Customer
    public static Customer create(String name, String email,
                                  String company, CustomerType type) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name is required");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Valid email is required");
        }
        return new Customer(UUID.randomUUID(), name, email, company, type);
    }

    private Customer(UUID id, String name, String email,
                     String company, CustomerType type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.company = company;
        this.type = type;
        this.auditInfo = new AuditInfo("system");
    }

    // Business behavior — update contact details
    public void updateContactInfo(String name, String email) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
        this.name = name;
        this.email = email;
        this.auditInfo.markUpdated("system");
    }

    // Getters — no setters (state changes only through behavior methods)
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCompany() { return company; }
    public CustomerType getType() { return type; }
    public AuditInfo getAuditInfo() { return auditInfo; }
}