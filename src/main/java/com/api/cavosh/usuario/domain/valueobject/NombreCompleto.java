package com.api.cavosh.usuario.domain.valueobject;

/**
 * Representa el nombre completo de un Usuario
 *
 * @param value nombre completo normalizado de un Usuario
 */
public record NombreCompleto(String value) {

    // Constructor compacto que valida y normaliza el nombre completo de un Usuario
    public NombreCompleto {
        if(value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre completo no debe estar en blanco"
            );
        }

        value = value.strip();

        if(value.length() > 150) {
            throw new IllegalArgumentException(
                    "El nombre completo no debe exceder los 150 caracteres"
            );
        }
    }
}
