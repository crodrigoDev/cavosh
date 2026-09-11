package com.api.cavosh.producto.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Identifica una categoria del catalogo
 *
 * @param value identificador de la categoria
 */
public record CategoriaId(UUID value) {

    /**
     * Verifica que el identificador tenga un valor
     */
    public CategoriaId {
        Objects.requireNonNull(value, "El id de la categoria no puede ser null");
    }

    /**
     * Crea un identificador nuevo
     *
     * @return nuevo identificador de categoria
     */
    public static CategoriaId newId() {
        return new CategoriaId(UUID.randomUUID());
    }

    /**
     * Convierte un texto UUID en un identificador de categoria
     *
     * @param value UUID recibido como texto
     * @return identificador reconstruido
     */
    public static CategoriaId fromString(String value) {
        Objects.requireNonNull(value, "El id de la categoria no puede ser null");
        return new CategoriaId(UUID.fromString(value));
    }
}
