package com.api.cavosh.shared.adapter.in.web.response;

/**
 * Representa un error de validacion asociado a un campo recibido por la API
 *
 * @param field nombre del campo invalido
 * @param message mensaje que explica la regla incumplida
 */
public record ApiFieldError(
        String field,
        String message
) {
}
