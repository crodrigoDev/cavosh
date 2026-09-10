package com.api.cavosh.configuration;

import com.api.cavosh.configuration.properties.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtAudienceValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Configura los componentes criptograficos utilizados para generar y validar JWT
 */
@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtConfiguration {

    private static final int MINIMO_HS256_KEY_BYTES = 32;

    /**
     * Construye la clave simetrica utilizada para firmar y verificar JWT
     *
     * @param properties configuración externa de JWT
     * @return clave HMAC construida a partir del secreto en Base64
     * @throws IllegalStateException cuando el secreto no es Base64 válido o es muy corto
     */
    @Bean
    public SecretKey jwtSecretKey(JwtProperties properties) {
        final byte[] secretBytes;

        try {
            secretBytes = Base64.getDecoder().decode(
                    properties.secretBase64().strip()
            );
        } catch (IllegalArgumentException exception) {
            throw new IllegalStateException(
                    "JWT_SECRET_BASE64 debe contener un valor Base64 válido",
                    exception
            );
        }

        if (secretBytes.length < MINIMO_HS256_KEY_BYTES)
            throw new IllegalStateException(
                    "JWT_SECRET_BASE64 debe representar al menos 32 bytes"
            );

        return new SecretKeySpec(secretBytes, "HmacSHA256");
    }

    /**
     * Crea el encoder encargado de firmar JWT mediante HS256
     *
     * @param secretKey clave HMAC utilizada para firmar
     * @return encoder JWT configurado
     */
    @Bean
    public JwtEncoder jwtEncoder(SecretKey secretKey) {
        return NimbusJwtEncoder
                .withSecretKey(secretKey)
                .algorithm(MacAlgorithm.HS256)
                .build();
    }

    /**
     * Crea el decoder encargado de verificar firma, fechas, emisor y audiencia
     *
     * @param secretKey clave HMAC utilizada para verificar la firma
     * @param properties configuracion del emisor y la audiencia esperados
     * @return decoder JWT configurado
     */
    @Bean
    public JwtDecoder jwtDecoder(
            SecretKey secretKey,
            JwtProperties properties
    ) {
        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();

        OAuth2TokenValidator<Jwt> issuerValidator =
                JwtValidators.createDefaultWithIssuer(properties.issuer());
        OAuth2TokenValidator<Jwt> audienceValidator =
                new JwtAudienceValidator(properties.audience());

        decoder.setJwtValidator(
                new DelegatingOAuth2TokenValidator<>(
                        issuerValidator,
                        audienceValidator
                )
        );

        return decoder;
    }
}
