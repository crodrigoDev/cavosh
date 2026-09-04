package com.api.cavosh.usuario.infrastructure.adapter.in.web.request;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Representa el cuerpo HTTP necesario para registrar un usuario
 *
 * @param nombreCompleto nombre completo solicitado durante el registro
 * @param email email solicitado durante el registro
 * @param password contraseña en texto plano
 * @param passwordConfirmation confirmacion de contraseña
 */
public record RegistrarUsuarioRequest(

        @NotBlank(message = "El nombre completo es obligatorio")
        @Size(max = 150, message = "El nombre completo no puede exceder los 150 caracteres")
        String nombreCompleto,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        @Size(max = 320, message = "El email no puede exceder los 320 caracteres")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        String password,

        @NotBlank(message = "La confirmacion de contraseña es obligatoria")
        String passwordConfirmation
) {

    /**
     * Determina si la contraseña y su confirmacion coinciden
     *
     * @return {@code true} cuando ambas contraseñas coinciden
     */
    @AssertTrue(message = "Las contraseñas no coinciden")
    public boolean isPasswordConfirmationValid() {
        return password != null && password.equals(passwordConfirmation);
    }
}
