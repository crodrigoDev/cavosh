package com.api.cavosh.producto.application.port.in.producto.detalle;

import com.api.cavosh.producto.domain.valueobject.VarianteProductoId;

import java.math.BigDecimal;

/**
 * Contiene una variante disponible de un producto
 *
 * @param id identificador de la variante
 * @param nombre nombre visible
 * @param precio precio actual
 * @param moneda codigo de moneda
 */
public record VarianteProductoResult(
        VarianteProductoId id,
        String nombre,
        BigDecimal precio,
        String moneda
) {
}
