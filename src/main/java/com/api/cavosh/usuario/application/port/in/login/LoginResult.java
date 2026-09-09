package com.api.cavosh.usuario.application.port.in.login;

import com.api.cavosh.usuario.domain.valueobject.UsuarioId;

/**
 * Agrupa la informacion devuelta despues de un inicio de sesion exitoso
 *
 * @param id identificador del usuario autenticado
 * @param nombreCompleto nombre que puede mostrar la aplicacion
 * @param email correo electronico normalizado
 * @param accessToken token utilizado para autorizar las siguientes peticiones
 * @param expiracionSegundos tiempo de vigencia del access token en segundos
 */
public record LoginResult(
        UsuarioId id,
        String nombreCompleto,
        String email,
        String accessToken,
        long expiracionSegundos
) {
}
