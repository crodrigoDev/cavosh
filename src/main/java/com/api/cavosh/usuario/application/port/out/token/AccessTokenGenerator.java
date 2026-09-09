package com.api.cavosh.usuario.application.port.out.token;

import com.api.cavosh.usuario.domain.model.Usuario;

/**
 * Puerto de salida encargado de generar access tokens para usuarios autenticados
 */
public interface AccessTokenGenerator {

    /**
     * Genera un access token para el usuario indicado
     *
     * @param usuario usuario cuya identidad fue identificada
     * @return access token generado y su tiempo de vigencia
     */
    AccessToken generate(Usuario usuario);
}
