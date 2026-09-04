package com.api.cavosh.usuario.application.port.in;

import com.api.cavosh.usuario.application.command.RegistrarUsuarioCommand;
import com.api.cavosh.usuario.application.result.RegistrarUsuarioResult;

/**
 * Puerto de entrada para registrar usuarios
 */
public interface RegistrarUsuarioUseCase {

    /**
     * Registra un Usuario sin exponer detalles HTTP o persistencia
     *
     * @param command datos requeridos para el registro
     * @return datos seguros del usuario recien registrado
     */
    RegistrarUsuarioResult ejecutar(RegistrarUsuarioCommand command);
}
