package com.api.cavosh.producto.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Identifica una variante de producto
 *
 * @param value identificador de la variante
 */
public record VarianteProductoId(UUID value) {

    /**
     * Verifica que el identificador tenga un valor
     */
    public VarianteProductoId {
        Objects.requireNonNull(value, "El id de la variante no puede ser null");
    }

    /**
     * Crea un identificador nuevo
     *
     * @return nuevo identificador de variante
     */
    public static VarianteProductoId newId() {
        return new VarianteProductoId(UUID.randomUUID());
    }
}
