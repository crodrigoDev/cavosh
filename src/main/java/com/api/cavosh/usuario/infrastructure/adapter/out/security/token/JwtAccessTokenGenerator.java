package com.api.cavosh.usuario.infrastructure.adapter.out.security.token;

import com.api.cavosh.configuration.properties.JwtProperties;
import com.api.cavosh.usuario.application.port.out.token.AccessToken;
import com.api.cavosh.usuario.application.port.out.token.AccessTokenGenerator;
import com.api.cavosh.usuario.domain.model.Usuario;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Implementa la generacion de access tokens mediante JWT firmados con HS256
 */
@Component
public final class JwtAccessTokenGenerator implements AccessTokenGenerator {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties properties;
    private final Clock clock;

    /**
     * Construye el adaptador con sus dependencias criptograficas y temporales
     *
     * @param jwtEncoder encoder utilizado para firmar los tokens
     * @param properties configuracion utilizada para construir los claims
     * @param clock reloj utilizado para calcular creación y expiración
     */
    public JwtAccessTokenGenerator(
            JwtEncoder jwtEncoder,
            JwtProperties properties,
            Clock clock
    ) {
        this.jwtEncoder = Objects.requireNonNull(
                jwtEncoder,
                "El encoder JWT no puede ser null"
        );
        this.properties = Objects.requireNonNull(
                properties,
                "Las propiedades JWT no pueden ser null"
        );
        this.clock = Objects.requireNonNull(
                clock,
                "El reloj no puede ser null"
        );
    }

    /**
     * Genera un access token para el usuario cuya identidad ya fue verificada
     *
     * @param usuario usuario autenticado
     * @return token firmado y su tiempo de vigencia en segundos
     */
    @Override
    public AccessToken generate(Usuario usuario) {
        Objects.requireNonNull(
                usuario,
                "El usuario no puede ser null"
        );

        Instant issuedAt = Instant.now(clock);
        Instant expiresAt = issuedAt.plus(properties.accessTokenExpiration());

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(properties.issuer())
                .subject(usuario.id().value().toString())
                .audience(List.of(properties.audience()))
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .id(UUID.randomUUID().toString())
                .build();

        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .build();

        Jwt jwt = jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)
        );

        return new AccessToken(
                jwt.getTokenValue(),
                properties.accessTokenExpiration().toSeconds()
        );
    }
}
