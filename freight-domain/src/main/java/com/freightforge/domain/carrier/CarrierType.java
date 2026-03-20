package com.freightforge.domain.carrier;

public enum CarrierType {
    AIR,        // Airlines — fastest, most expensive
    SEA,        // Shipping lines — slowest, cheapest for heavy cargo
    ROAD,       // Trucking — door to door
    RAIL,       // Train — good for landlocked countries
    MULTIMODAL; // Combination of the above
}