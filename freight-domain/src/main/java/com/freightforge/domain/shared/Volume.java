package com.freightforge.domain.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Volume(BigDecimal cbm) {

    public Volume {
        if (cbm == null || cbm.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Volume must be greater than zero");
        }
        cbm = cbm.setScale(4, RoundingMode.HALF_UP);
    }

    public static Volume ofCBM(double cbm) {
        return new Volume(BigDecimal.valueOf(cbm));
    }

    public Volume add(Volume other) {
        return new Volume(this.cbm.add(other.cbm));
    }

    @Override
    public String toString() {
        return cbm.toPlainString() + " CBM";
    }
}