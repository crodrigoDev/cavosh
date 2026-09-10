package com.api.cavosh.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Objects;

/**
 * Agrupa la configuracion utilizada para generar y validar JWT.
 *
 * @param secretBase64 clave secreta codificada en Base64
 * @param issuer identificador del emisor del token
 * @param audience destinatario esperado del token
 * @param accessTokenExpiration duración del access token
 */
@ConfigurationProperties(prefix = "cavosh.security.jwt")
public record JwtProperties(
        String secretBase64,
        String issuer,
        String audience,
        Duration accessTokenExpiration
) {

    /**
     * Valida que la configuracion JWT tenga todos los valores requeridos
     *
     * @param secretBase64 clave secreta codificada en Base64
     * @param issuer identificador del emisor del token
     * @param audience destinatario esperado del token
     * @param accessTokenExpiration duracion del access token
     */
    public JwtProperties {
        validarTexto(secretBase64, "La clave JWT es obligatoria");
        validarTexto(issuer, "El emisor del JWT es obligatorio");
        validarTexto(audience, "La audiencia del JWT es obligatoria");

        Objects.requireNonNull(
                accessTokenExpiration,
                "La duracion del access token es obligatoria"
        );

        if (accessTokenExpiration.compareTo(Duration.ofSeconds(1)) < 0)
            throw new IllegalArgumentException(
                    "La duracion del access token debe ser de al menos un segundo"
            );
    }


    /**
     * Comprueba que una propiedad textual tenga contenido
     *
     * @param value valor configurado
     * @param message mensaje utilizado cuando el valor es inválido
     */
    private static void validarTexto(String value, String message) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException(message);
    }
}
