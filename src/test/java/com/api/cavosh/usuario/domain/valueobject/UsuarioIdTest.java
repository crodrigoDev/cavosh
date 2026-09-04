package com.api.cavosh.usuario.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioIdTest {

    @Test
    void crearIdNoNuloValido() {
        UsuarioId id = UsuarioId.newId();

        assertNotNull(id);
        assertNotNull(id.value());
    }

    @Test
    void reconstruirIdDesdeUnStringValido() {
        String idString = "550e8400-e29b-41d4-a716-446655440000";
        UsuarioId id = UsuarioId.fromString(idString);

        assertEquals(UUID.fromString(idString), id.value());
    }

    @Test
    void rechazarReconstruccionDeUnIdStringInvalido() {
        assertThrows(
                IllegalArgumentException.class,
                () -> UsuarioId.fromString("hola")
        );
    }

    @Test
    void rechazarReconstruccionDeUnIdStringNulo() {
        assertThrows(
                NullPointerException.class,
                () -> UsuarioId.fromString(null)
        );
    }
}
