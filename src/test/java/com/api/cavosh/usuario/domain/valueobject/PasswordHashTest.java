package com.api.cavosh.usuario.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordHashTest {

    @Test
    void rechazarUnPasswordHashNuloOEnBlanco() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PasswordHash(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new PasswordHash(" ")
        );
    }
}
