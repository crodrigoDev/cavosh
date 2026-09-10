package com.api.cavosh.usuario.application.service;

import com.api.cavosh.usuario.application.exception.InvalidCredentialsException;
import com.api.cavosh.usuario.application.port.in.login.LoginCommand;
import com.api.cavosh.usuario.application.port.in.login.LoginResult;
import com.api.cavosh.usuario.application.port.in.login.LoginUseCase;
import com.api.cavosh.usuario.application.port.out.PasswordHasher;
import com.api.cavosh.usuario.application.port.out.UsuarioRepository;
import com.api.cavosh.usuario.application.port.out.token.AccessToken;
import com.api.cavosh.usuario.application.port.out.token.AccessTokenGenerator;
import com.api.cavosh.usuario.domain.model.Usuario;
import com.api.cavosh.usuario.domain.valueobject.Email;

import java.util.Objects;

/**
 * Implementa el inicio de sesion mediante email y contraseña
 */
public final class LoginService implements LoginUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordHasher passwordHasher;
    private final AccessTokenGenerator accessTokenGenerator;

    /**
     * Construye el servicio con las dependencias requeridas para autenticar usuarios
     *
     * @param usuarioRepository repositorio utilizado para buscar usuarios
     * @param passwordHasher componente utilizado para verificar contraseñas
     * @param accessTokenGenerator componente utilizado para generar access tokens
     */
    public LoginService(
            UsuarioRepository usuarioRepository,
            PasswordHasher passwordHasher,
            AccessTokenGenerator accessTokenGenerator
    ){
        this.usuarioRepository = Objects.requireNonNull(
                usuarioRepository,
                "El repository de usuarios no puede ser null"
        );
        this.passwordHasher = Objects.requireNonNull(
                passwordHasher,
                "El verificador de contraseñas no puede ser null"
        );
        this.accessTokenGenerator = Objects.requireNonNull(
                accessTokenGenerator,
                "El generador de access tokens no puede ser null"
        );
    }

    /**
     * Verifica las credenciales y genera un access token cuando son correctas
     *
     * @param command credenciales proporcionadas por el usuario
     * @return informacion segura del usuario y el access token generado
     * @throws InvalidCredentialsException cuando el usuario no existe, el email
     *                                     es invalido o la contraseña no coincide
     */
    @Override
    public LoginResult ejecutar(LoginCommand command) {
        Objects.requireNonNull(
                command,
                "Las credenciales no pueden ser null"
        );

        Email email = crearEmail(command.email());
        validarPassword(command.password());

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        boolean passwordValida = passwordHasher.matches(
                command.password(),
                usuario.passwordHash()
        );

        if(!passwordValida)
            throw new InvalidCredentialsException();

        AccessToken accessToken = Objects.requireNonNull(
                accessTokenGenerator.generate(usuario),
                "El access token generado no puede ser null"
        );

        return new LoginResult(
                usuario.id(),
                usuario.nombreCompleto().value(),
                usuario.email().value(),
                accessToken.value(),
                accessToken.expiracionSegundos()
        );
    }

    /**
     * Convierte el email recibido al Value Object correspondiente
     *
     * @param email correo electronico recibido
     * @return email normalizado y validado
     * @throws InvalidCredentialsException cuando el email no tiene un formato válido
     */
    private Email crearEmail(String email) {
        try {
            return new Email(email);
        } catch (IllegalArgumentException exception) {
            throw new InvalidCredentialsException();
        }
    }

    /**
     * Comprueba que se haya proporcionado una contraseña
     *
     * @param password contraseña recibida en texto plano
     * @throws InvalidCredentialsException cuando la contraseña está vacía
     */
    private void validarPassword(String password) {
        if (password == null || password.isBlank()) {
            throw new InvalidCredentialsException();
        }
    }
}
