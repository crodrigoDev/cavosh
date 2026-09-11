package com.api.cavosh.producto.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

/**
 * Representa el precio y la moneda de una variante
 *
 * @param monto cantidad cobrada
 * @param moneda codigo ISO de la moneda
 */
public record Precio(BigDecimal monto, String moneda) {

    /**
     * Normaliza el monto y valida la moneda recibida
     */
    public Precio {
        Objects.requireNonNull(monto, "El monto no puede ser null");
        Objects.requireNonNull(moneda, "La moneda no puede ser null");

        if (monto.signum() < 0)
            throw new IllegalArgumentException("El monto no puede ser negativo");

        monto = monto.setScale(2, RoundingMode.HALF_UP);
        moneda = moneda.trim().toUpperCase(Locale.ROOT);
        Currency.getInstance(moneda);
    }
}
