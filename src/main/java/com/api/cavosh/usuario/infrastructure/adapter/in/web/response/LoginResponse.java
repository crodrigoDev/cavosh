package com.api.cavosh.usuario.infrastructure.adapter.in.web.response;

import java.util.UUID;

/**
 * Representa los datos devueltos despues de iniciar sesion.
 *
 * @param id identificador del usuario
 * @param nombreCompleto nombre completo del usuario
 * @param email correo del usuario
 * @param accessToken token para acceder a las rutas protegidas
 * @param tokenType tipo de token entregado
 * @param expiracionSegundos tiempo de vigencia del token en segundos
 */
public record LoginResponse(
        UUID id,
        String nombreCompleto,
        String email,
        String accessToken,
        String tokenType,
        long expiracionSegundos
) {
}
