package com.api.cavosh.usuario.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

/**
 * Value object que representa el ID de un Usuario
 *
 * @param value Id de un Usuario
 */
public record UsuarioId(UUID value) {

    // Constructor compacto que verifica que el id no sea null
    public UsuarioId {
        Objects.requireNonNull(
                value,
                "El UUID no puede ser null"
        );
    }

    // Metodo fábrica que crea un id de Usuario
    public static UsuarioId newId() {
        return new UsuarioId(UUID.randomUUID());
    }

    // Metodo estatico que reconstruye un UsuarioId a partir de un string
    public static UsuarioId fromString(String id){
        Objects.requireNonNull(
                id,
                "El id string no puede ser null"
        );
        return new UsuarioId(UUID.fromString(id));
    }
}
