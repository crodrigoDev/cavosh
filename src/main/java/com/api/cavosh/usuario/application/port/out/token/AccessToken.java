package com.api.cavosh.usuario.application.port.out.token;

import java.util.Objects;

/**
 * Representa un access token generado por la aplicacion
 *
 * @param value contenido firmado del token
 * @param expiracionSegundos tiempo de vigencia en segundos
 */
public record AccessToken(
        String value,
        long expiracionSegundos
) {

    /**
     * Valida las propiedades del access token
     *
     * @param value contenido firmado del token
     * @param expiracionSegundos tiempo de vigencia en segundos
     */
    public AccessToken {
        Objects.requireNonNull(value, "El access token no puede ser null");

        if(value.isBlank()) {
            throw new IllegalArgumentException(
                    "El access token no debe estar vacio"
            );
        }

        if(expiracionSegundos <= 0) {
            throw new IllegalArgumentException(
                    "La duración del access token debe ser positiva"
            );
        }
    }
}
