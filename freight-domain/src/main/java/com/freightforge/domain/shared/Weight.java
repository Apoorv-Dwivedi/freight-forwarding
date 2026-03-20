package com.freightforge.domain.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Weight(BigDecimal valueInKg) {

    public Weight {
        if (valueInKg == null || valueInKg.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Weight must be greater than zero");
        }
        valueInKg = valueInKg.setScale(3, RoundingMode.HALF_UP);
    }

    public static Weight ofKilograms(double kg) {
        return new Weight(BigDecimal.valueOf(kg));
    }

    public static Weight ofPounds(double lbs) {
        // 1 pound = 0.453592 kg
        return new Weight(BigDecimal.valueOf(lbs * 0.453592));
    }

    public Weight add(Weight other) {
        return new Weight(this.valueInKg.add(other.valueInKg));
    }

    @Override
    public String toString() {
        return valueInKg.toPlainString() + " kg";
    }
}