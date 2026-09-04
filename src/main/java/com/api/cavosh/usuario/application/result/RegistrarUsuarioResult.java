package com.api.cavosh.usuario.application.result;

import com.api.cavosh.usuario.domain.valueobject.UsuarioId;

/**
 * Agrupa la información devuelta al registrar un Usuario
 *
 * @param id identidad del Usuario
 * @param nombreCompleto nombre completo normalizado
 * @param email email normalizado
 */
public record RegistrarUsuarioResult(
        UsuarioId id,
        String nombreCompleto,
        String email
) {
}
