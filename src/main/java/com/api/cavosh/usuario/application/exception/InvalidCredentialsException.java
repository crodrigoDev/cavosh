package com.api.cavosh.usuario.application.exception;

/**
 * Indica que las credenciales proporcionadas no pueden autenticar al usuario
 */
public final class InvalidCredentialsException extends RuntimeException {

    /**
     * Construye la excepcion con un mensaje generico para que no se revele
     * si el email ya esta registrado en la base de datos
     */
    public InvalidCredentialsException() {
        super("El email o la contraseña son incorrectos");
    }
}
