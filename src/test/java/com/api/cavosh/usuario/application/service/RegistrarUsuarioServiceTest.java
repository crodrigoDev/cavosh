package com.api.cavosh.usuario.application.service;

import com.api.cavosh.usuario.application.command.RegistrarUsuarioCommand;
import com.api.cavosh.usuario.application.exception.EmailAlreadyRegisteredException;
import com.api.cavosh.usuario.application.port.out.PasswordHasher;
import com.api.cavosh.usuario.application.result.RegistrarUsuarioResult;
import com.api.cavosh.usuario.domain.model.Usuario;
import com.api.cavosh.usuario.application.port.out.UsuarioRepository;
import com.api.cavosh.usuario.domain.valueobject.Email;
import com.api.cavosh.usuario.domain.valueobject.PasswordHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Verifica el comportamiento del caso de uso de registro de usuarios.
 */
@ExtendWith(MockitoExtension.class)
class RegistrarUsuarioServiceTest {

    private static final Instant FECHA = Instant.parse(
            "2026-09-03T12:00:00Z"
    );

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordHasher passwordHasher;

    private RegistrarUsuarioService registrarUsuarioService;

    /**
     * Crea el caso de uso con un reloj fijo antes de cada prueba
     */
    @BeforeEach
    void setUp() {
        Clock clock = Clock.fixed(FECHA, ZoneOffset.UTC);

        registrarUsuarioService = new RegistrarUsuarioService(
                usuarioRepository,
                passwordHasher,
                clock
        );
    }

    /**
     * Registra un usuario cuando el email no le pertenece a otra cuenta
     */
    @Test
    void registrarUsuarioConEmailDisponible() {
        RegistrarUsuarioCommand command = new RegistrarUsuarioCommand(
                " Rodrigo Castillo ",
                " rodrigo@GMAIL.com ",
                "password-segura"
        );
        PasswordHash passwordHash = new PasswordHash("hash-seguro");

        when(usuarioRepository.existsByEmail(
                new Email("rodrigo@gmail.com")
        )).thenReturn(false);
        when(passwordHasher.hash("password-segura"))
                .thenReturn(passwordHash);
        when(usuarioRepository.save(any(Usuario.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RegistrarUsuarioResult result =
                registrarUsuarioService.ejecutar(command);

        ArgumentCaptor<Usuario> usuarioCaptor =
                ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(usuarioCaptor.capture());

        Usuario usuarioGuardado = usuarioCaptor.getValue();

        assertEquals(usuarioGuardado.id(), result.id());
        assertEquals("Rodrigo Castillo", result.nombreCompleto());
        assertEquals("rodrigo@gmail.com", result.email());
        assertEquals(passwordHash, usuarioGuardado.passwordHash());
        assertEquals(FECHA, usuarioGuardado.createdAt());
        assertEquals(FECHA, usuarioGuardado.updatedAt());

        verify(passwordHasher).hash("password-segura");
    }

    /**
     * Rechaza el registro cuando el email ya esta registrado
     */
    @Test
    void rechazarRegistroConEmailYaRegistrado() {
        RegistrarUsuarioCommand command = new RegistrarUsuarioCommand(
                "Rodrigo Castillo",
                "rodrigo@gmail.com",
                "password-segura"
        );

        when(usuarioRepository.existsByEmail(any(Email.class)))
                .thenReturn(true);

        assertThrows(
                EmailAlreadyRegisteredException.class,
                () -> registrarUsuarioService.ejecutar(command)
        );

        verify(usuarioRepository, never()).save(any(Usuario.class));
        verifyNoInteractions(passwordHasher);
    }

    /**
     * Rechaza el registro cuando la contraseña no contiene texto
     */
    @Test
    void rechazarRegistroConPasswordEnBlanco() {
        RegistrarUsuarioCommand command = new RegistrarUsuarioCommand(
                "Rodrigo Castillo",
                "rodrigo@gmail.com",
                " "
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> registrarUsuarioService.ejecutar(command)
        );

        verifyNoInteractions(usuarioRepository, passwordHasher);
    }
}
