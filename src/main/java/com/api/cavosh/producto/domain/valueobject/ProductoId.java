package com.api.cavosh.producto.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Identifica un producto dentro del catalogo
 *
 * @param value identificador del producto
 */
public record ProductoId(UUID value) {

    /**
     * Verifica que el identificador tenga un valor
     */
    public ProductoId {
        Objects.requireNonNull(value, "El id del producto no puede ser null");
    }

    /**
     * Crea un identificador nuevo
     *
     * @return nuevo identificador de producto
     */
    public static ProductoId newId() {
        return new ProductoId(UUID.randomUUID());
    }

    /**
     * Convierte un texto UUID en un identificador de producto
     *
     * @param value UUID recibido como texto
     * @return identificador reconstruido
     */
    public static ProductoId fromString(String value) {
        Objects.requireNonNull(value, "El id del producto no puede ser null");
        return new ProductoId(UUID.fromString(value));
    }
}
