package com.api.cavosh.usuario.domain.valueobject;

/**
 * Value object que representa el hash de la contraseña de un Usuario
 *
 * @param value hash de la contraseña de un Usuario
 */
public record PasswordHash(String value) {

    // Constructor compacto que valida el hash de la contraseña
    public PasswordHash {
        if(value == null || value.isBlank()){
            throw new IllegalArgumentException(
                    "El hash de la contraseña no puede estar en blanco"
            );
        }
    }
}
