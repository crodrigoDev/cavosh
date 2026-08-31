package com.api.cavosh.usuario.domain.valueobject;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Value object que representa un Email de un Usuario
 *
 * @param value Email de un Usuario
 */
public record Email(String value) {

    // Instancia unica de un Patron de Email
    private static final Pattern EMAIL_VALIDO = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE
    );

    // Constructor compacto que normaliza y valida un email
    public Email {
        if(value == null || value.isBlank()){
            throw new IllegalArgumentException(
                    "El email no puede estar en blanco"
            );
        }

        value = value.strip().toLowerCase(Locale.ROOT);

        if(!EMAIL_VALIDO.matcher(value).matches()){
            throw new IllegalArgumentException(
                    "Formato de email inválido"
            );
        }
    }
}
