package com.api.cavosh.usuario.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa la persistencia JPA de un usuario en la tabla {@code usuarios}
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(name = "email", nullable = false, unique = true, length = 320)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    /**
     * Crea una entidad de usuario lista para persistir
     *
     * @param id identificador del usuario
     * @param nombreCompleto nombre completo normalizado
     * @param email email normalizado y unico
     * @param passwordHash hash de contraseña
     * @param createdAt fecha de creacion
     * @param updatedAt fecha de ultima actualizacion
     */
    public UsuarioEntity(
            UUID id,
            String nombreCompleto,
            String email,
            String passwordHash,
            Instant createdAt,
            Instant updatedAt
    ){
        this.id = Objects.requireNonNull(
                id,
                "El id no puede ser null"
        );
        this.nombreCompleto = Objects.requireNonNull(
                nombreCompleto,
                "El nombre completo no puede ser null"
        );
        this.email = Objects.requireNonNull(
                email,
                "El email no puede ser null"
        );
        this.passwordHash = Objects.requireNonNull(
                passwordHash,
                "El hash de la contraseña no puede ser null"
        );
        this.createdAt = Objects.requireNonNull(
                createdAt,
                "La fecha de creacion no puede ser null"
        );
        this.updatedAt = Objects.requireNonNull(
                updatedAt,
                "La fecha de actualizacion no puede ser null"
        );
    }
}
