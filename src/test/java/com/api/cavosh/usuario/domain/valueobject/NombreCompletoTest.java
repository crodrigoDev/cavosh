package com.api.cavosh.usuario.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NombreCompletoTest {

    @Test
    void normalizarUnNombreCompleto() {
        NombreCompleto nombreCompleto = new NombreCompleto(" Rodrigo Castillo ");

        assertEquals("Rodrigo Castillo", nombreCompleto.value());
    }

    @Test
    void rechazarUnNombreCompletoNuloOEnBlanco() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new NombreCompleto(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new NombreCompleto(" ")
        );
    }

    @Test
    void rechazarUnNombreCompletoMuyExtenso() {
        String nombreExtenso = "a".repeat(160);

        assertThrows(
                IllegalArgumentException.class,
                () -> new NombreCompleto(
                        nombreExtenso
                )
        );
    }
}
