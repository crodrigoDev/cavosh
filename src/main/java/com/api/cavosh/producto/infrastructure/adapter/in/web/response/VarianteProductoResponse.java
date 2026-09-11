package com.api.cavosh.producto.infrastructure.adapter.in.web.response;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Representa una variante que el cliente puede elegir
 *
 * @param id identificador de la variante
 * @param nombre nombre visible
 * @param precio precio actual
 * @param moneda codigo de moneda
 */
public record VarianteProductoResponse(
        UUID id,
        String nombre,
        BigDecimal precio,
        String moneda
) {
}
