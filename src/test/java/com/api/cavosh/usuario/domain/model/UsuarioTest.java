package com.api.cavosh.usuario.domain.model;

import com.api.cavosh.usuario.domain.valueobject.Email;
import com.api.cavosh.usuario.domain.valueobject.NombreCompleto;
import com.api.cavosh.usuario.domain.valueobject.PasswordHash;
import com.api.cavosh.usuario.domain.valueobject.UsuarioId;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private static final Instant CREATED_AT = Instant.parse("2026-08-31T10:00:00Z");

    @Test
    void registrarUnUsuarioValido() {
        Usuario usuario = Usuario.register(
                new NombreCompleto(" Rodrigo Castillo "),
                new Email(" rodrigo@GMAIL.com "),
                new PasswordHash("hashed-password"),
                CREATED_AT,
                CREATED_AT
        );

        assertNotNull(usuario.id());
        assertEquals("Rodrigo Castillo", usuario.nombreCompleto().value());
        assertEquals("rodrigo@gmail.com", usuario.email().value());
        assertEquals("hashed-password", usuario.passwordHash().value());
        assertEquals(CREATED_AT, usuario.createdAt());
        assertEquals(CREATED_AT, usuario.updatedAt());
    }

    @Test
    void reconstruirUnUsuarioExistente() {
        UsuarioId id = UsuarioId.newId();

        Usuario usuario = Usuario.reconstitute(
                id,
                new NombreCompleto("Rodrigo Castillo"),
                new Email("rodrigo@gmail.com"),
                new PasswordHash("hashed-password"),
                CREATED_AT,
                CREATED_AT
        );

        assertEquals(id.value(), usuario.id().value());
        assertEquals("Rodrigo Castillo", usuario.nombreCompleto().value());
        assertEquals("rodrigo@gmail.com", usuario.email().value());
        assertEquals("hashed-password", usuario.passwordHash().value());
        assertEquals(CREATED_AT, usuario.createdAt());
        assertEquals(CREATED_AT, usuario.updatedAt());
    }

    @Test
    void rechazarActualizacionAnteriorALaCreacion() {
        Instant updatedAt = CREATED_AT.minusSeconds(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> Usuario.register(
                        new NombreCompleto(" Rodrigo Castillo "),
                        new Email(" rodrigo@GMAIL.com "),
                        new PasswordHash("hashed-password"),
                        CREATED_AT,
                        updatedAt
                )
        );
    }
}
