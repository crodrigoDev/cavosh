package com.api.cavosh.usuario.application.port.out;

import com.api.cavosh.usuario.domain.valueobject.PasswordHash;

/**
 * Puerto de salida utilizado para proteger las contraseñas
 */
public interface PasswordHasher {

    /**
     * Genera un hash de la contraseña
     *
     * @param password contraseña en texto plano
     * @return hash apto para persistencia
     */
    PasswordHash hash(String password);
}
