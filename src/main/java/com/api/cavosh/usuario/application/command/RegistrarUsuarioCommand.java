package com.api.cavosh.usuario.application.command;

/**
 * Agrupa los datos que la aplicación necesita para registrar un Usuario
 *
 * @param nombreCompleto nombre completo del usuario
 * @param email dirección de correo electrónico
 * @param password contraseña en texto plano
 */
public record RegistrarUsuarioCommand(
        String nombreCompleto,
        String email,
        String password
) {
}
