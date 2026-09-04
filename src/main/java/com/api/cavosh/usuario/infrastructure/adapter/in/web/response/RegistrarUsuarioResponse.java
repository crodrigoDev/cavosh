package com.api.cavosh.usuario.infrastructure.adapter.in.web.response;

import java.util.UUID;

/**
 * Representa los datos seguros devueltos despues de registrar un usuario
 *
 * @param id identificador del usuario registrado
 * @param nombreCompleto nombre completo normalizado
 * @param email email normalizdo
 */
public record RegistrarUsuarioResponse(
        UUID id,
        String nombreCompleto,
        String email
) {
}
