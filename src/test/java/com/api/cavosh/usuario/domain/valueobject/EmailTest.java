package com.api.cavosh.usuario.domain.valueobject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    void normalizarUnEmailValido() {
        Email email = new Email(" rodrigo@GMAIL.COM");
        assertEquals("rodrigo@gmail.com", email.value());
    }

    @Test
    void rechazarUnEmailInvalido() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Email("rodrigo")
        );
    }

    @Test
    void rechazarUnEmailNuloOEnBlanco() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Email(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Email(" ")
        );
    }
}
