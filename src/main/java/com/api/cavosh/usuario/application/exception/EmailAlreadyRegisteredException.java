package com.api.cavosh.usuario.application.exception;

/**
 * Se lanza cuando se intenta registrar un usuario con un email
 * que ya pertenece a otra cuenta
 */
public final class EmailAlreadyRegisteredException extends RuntimeException {

    /**
     * Crea la excepción para indicar que el email ya esta registrado.
     */
    public EmailAlreadyRegisteredException() {
        super("El email ya se encuentra registrado");
    }
}
