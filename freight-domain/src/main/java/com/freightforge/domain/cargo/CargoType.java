package com.freightforge.domain.cargo;

public enum CargoType {
    GENERAL,        // Standard goods, no special handling
    PERISHABLE,     // Food, medicine — needs temperature control
    HAZARDOUS,      // Dangerous goods — UN classification required
    OVERSIZED,      // Exceeds standard container dimensions
    LIQUID_BULK,    // Oils, chemicals in tankers
    DRY_BULK,       // Grain, coal, ore
    VEHICLE,        // Cars, trucks, machinery
    LIVE_ANIMALS;   // Requires special permits and handling

    public boolean requiresSpecialHandling() {
        return this == PERISHABLE || this == HAZARDOUS
                || this == LIVE_ANIMALS || this == OVERSIZED;
    }
}