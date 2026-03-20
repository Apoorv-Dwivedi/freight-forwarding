package com.freightforge.domain.document;

public enum DocumentType {
    BILL_OF_LADING,         // Primary shipping document — proof of contract
    COMMERCIAL_INVOICE,     // Invoice for customs — declares goods value
    PACKING_LIST,           // Detailed list of contents per package
    CERTIFICATE_OF_ORIGIN,  // Proves where goods were manufactured
    CUSTOMS_DECLARATION,    // Filed with customs authority
    INSURANCE_CERTIFICATE,  // Proof of cargo insurance
    DANGEROUS_GOODS_DECLARATION; // Required for hazardous cargo
}