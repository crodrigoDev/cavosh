package com.api.cavosh.usuario.application.port.in.login;

/**
 * Define la operacion disponible para iniciar sesion mediante contraseña
 */
public interface LoginUseCase {

    /**
     * Verifica las credenciales y genera una access token
     *
     * @param command credenciales proporcionadas por el usuairo
     * @return usuario autenticado y access token generado
     */
    LoginResult ejecutar(LoginCommand command);
}
