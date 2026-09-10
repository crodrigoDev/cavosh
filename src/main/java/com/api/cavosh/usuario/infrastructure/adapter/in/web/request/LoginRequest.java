package com.api.cavosh.usuario.infrastructure.adapter.in.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Representa los datos recibidos para iniciar sesion.
 *
 * @param email correo enviado por el usuario
 * @param password contraseña enviada por el usuario
 */
public record LoginRequest(

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        @Size(max = 320, message = "El email no puede exceder los 320 caracteres")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        String password
) {
}
