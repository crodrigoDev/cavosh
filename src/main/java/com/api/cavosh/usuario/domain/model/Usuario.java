package com.api.cavosh.usuario.domain.model;

import com.api.cavosh.usuario.domain.valueobject.Email;
import com.api.cavosh.usuario.domain.valueobject.NombreCompleto;
import com.api.cavosh.usuario.domain.valueobject.PasswordHash;
import com.api.cavosh.usuario.domain.valueobject.UsuarioId;

import java.time.Instant;
import java.util.Objects;

/**
 * Clase final que representa un Usuario y define como se constituye.
 * La clase no se puede heredar, evitando que las subclases alteren sus reglas de negocio.
 */
public final class Usuario {
    private final UsuarioId id;
    private final NombreCompleto nombreCompleto;
    private final Email email;
    private final PasswordHash passwordHash;
    private final Instant createdAt;
    private final Instant updatedAt;

    // Constructor privado para centralizar la creacion y validacion del Usuario
    private Usuario(
            UsuarioId id,
            NombreCompleto nombreCompleto,
            Email email,
            PasswordHash passwordHash,
            Instant createdAt,
            Instant updatedAt
    ){
        this.id = Objects.requireNonNull(
                id, "El ID no debe ser null"
        );
        this.nombreCompleto = Objects.requireNonNull(
                nombreCompleto, "El nombre completo no debe ser null"
        );
        this.email = Objects.requireNonNull(
                email, "El email no debe ser null"
        );
        this.passwordHash = Objects.requireNonNull(
                passwordHash, "El hash de la contraseña no debe ser null"
        );
        this.createdAt = Objects.requireNonNull(
                createdAt, "La fecha de creación no debe ser null"
        );
        this.updatedAt = Objects.requireNonNull(
                updatedAt, "La fecha de actualizacion no debe ser null"
        );
        if(updatedAt.isBefore(createdAt)){
            throw new IllegalArgumentException(
                    "La fecha de actualización no puede ser anterior a la fecha de creación"
            );
        }
    }

    // Metodo fabrica que crea una nueva instancia de un Usuario
    public static Usuario register(
            NombreCompleto nombreCompleto,
            Email email,
            PasswordHash passwordHash,
            Instant createdAt,
            Instant updatedAt
    ){
        return new Usuario(
                UsuarioId.newId(),
                nombreCompleto,
                email,
                passwordHash,
                createdAt,
                updatedAt
        );
    }

    // Metodo estatico que reconstruye un Usuario
    public static Usuario reconstitute(
            UsuarioId id,
            NombreCompleto nombreCompleto,
            Email email,
            PasswordHash passwordHash,
            Instant createdAt,
            Instant updatedAt
    ){
        return new Usuario(
                id,
                nombreCompleto,
                email,
                passwordHash,
                createdAt,
                updatedAt
        );
    }

    public UsuarioId id() {
        return id;
    }

    public NombreCompleto nombreCompleto() {
        return nombreCompleto;
    }

    public Email email() {
        return email;
    }

    public PasswordHash passwordHash() {
        return passwordHash;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}
