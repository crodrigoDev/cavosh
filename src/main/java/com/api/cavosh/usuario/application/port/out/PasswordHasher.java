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

    /**
     * Comprueba una contraseña en texto plano contra el hash almacenado
     *
     * @param password contraseña recibida durante el login
     * @param passwordHash hash almacenado del usuario
     * @return {@code true} cuando la contraseña coincide
     */
    boolean matches(String password, PasswordHash passwordHash);
}
