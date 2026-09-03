package com.api.cavosh.usuario.application.service;

import com.api.cavosh.usuario.application.command.RegistrarUsuarioCommand;
import com.api.cavosh.usuario.application.exception.EmailAlreadyRegisteredException;
import com.api.cavosh.usuario.application.port.in.RegistrarUsuarioUseCase;
import com.api.cavosh.usuario.application.port.out.PasswordHasher;
import com.api.cavosh.usuario.application.result.RegistrarUsuarioResult;
import com.api.cavosh.usuario.domain.model.Usuario;
import com.api.cavosh.usuario.domain.repository.UsuarioRepository;
import com.api.cavosh.usuario.domain.valueobject.Email;
import com.api.cavosh.usuario.domain.valueobject.NombreCompleto;
import com.api.cavosh.usuario.domain.valueobject.PasswordHash;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;

public final class RegistrarUsuarioService implements RegistrarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final PasswordHasher passwordHasher;
    private final Clock clock;

    /**
     * Crea el servicio con sus dependencias de aplicación
     *
     * @param usuarioRepository repositorio para consultar y guardar usuarios
     * @param passwordHasher servicio generado para generar hashes de contraseñas
     * @param clock reloj utilizado para obtener la fecha actual de forma testeable
     */
    public RegistrarUsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordHasher passwordHasher,
            Clock clock
    ){
        this.usuarioRepository = Objects.requireNonNull(
                usuarioRepository,
                "El repositorio de usuarios no puede ser null"
        );
        this.passwordHasher = Objects.requireNonNull(
                passwordHasher,
                "El hasher de contraseñas no puede ser null"
        );
        this.clock = Objects.requireNonNull(
                clock,
                "El reloj no puede ser null"
        );
    }

    /**
     * Registra un usuario nuevo cuando su email no esta asociado a otra cuenta
     *
     * @param command datos requeridos para el registro
     * @return informacion segura del usuario creado
     * @throws EmailAlreadyRegisteredException si el email ya se encuentra registrado
     * @throws IllegalArgumentException si la contraseña esta vacia o los datos
     *                                  cumplen las reglas del dominio
     */
    @Override
    public RegistrarUsuarioResult ejecutar(RegistrarUsuarioCommand command) {
        Objects.requireNonNull(
                command,
                "Los datos de registro no pueden ser null"
        );

        validarPassword(command.password());

        NombreCompleto nombreCompleto = new NombreCompleto(command.nombreCompleto());
        Email email = new Email(command.email());

        if(usuarioRepository.existsByEmail(email)){
            throw new EmailAlreadyRegisteredException();
        }

        PasswordHash passwordHash = passwordHasher.hash(command.password());
        Instant now = Instant.now(clock);

        Usuario usuario = Usuario.register(
                nombreCompleto,
                email,
                passwordHash,
                now,
                now
        );

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return new RegistrarUsuarioResult(
                usuarioGuardado.id(),
                usuarioGuardado.nombreCompleto().value(),
                usuarioGuardado.email().value()
        );
    }

    /**
     * Verifica que exista una contraseña antes de enviarla al servicio de hash
     *
     * @param password contraseña en texto plano
     * @throws IllegalArgumentException si la contraseña es nula o esta en blanco
     */
    private void validarPassword(String password) {
        if(password == null || password.isBlank()){
            throw new IllegalArgumentException(
                    "La contraseña no puede estar vacia"
            );
        }
    }
}
