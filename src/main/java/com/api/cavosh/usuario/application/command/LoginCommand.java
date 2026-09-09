package com.api.cavosh.usuario.application.command;

/**
 * Agrupa las credenciales necesarias para iniciar sesión mediante contraseña
 *
 * @param email correo electrónico proporcionado por el usuario
 * @param password contraseña proporcionada en texto plano
 */
public record LoginCommand(
        String email,
        String password
) {
}
