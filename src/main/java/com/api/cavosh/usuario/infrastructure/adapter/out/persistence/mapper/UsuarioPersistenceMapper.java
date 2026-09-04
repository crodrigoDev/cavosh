package com.api.cavosh.usuario.infrastructure.adapter.out.persistence.mapper;

import com.api.cavosh.usuario.domain.model.Usuario;
import com.api.cavosh.usuario.domain.valueobject.Email;
import com.api.cavosh.usuario.domain.valueobject.NombreCompleto;
import com.api.cavosh.usuario.domain.valueobject.PasswordHash;
import com.api.cavosh.usuario.domain.valueobject.UsuarioId;
import com.api.cavosh.usuario.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Convierte usuarios entre el modelo de dominio y la representacion JPA.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE) // Evita la creacion de instancias
public final class UsuarioPersistenceMapper {

    /**
     * Convierte un usuario de dominio en una entidad lista para persistir
     *
     * @param usuario usuario del dominio que se desea convertir
     * @return entidad JPA equivalente
     */
    public static UsuarioEntity toEntity(Usuario usuario) {
        Objects.requireNonNull(usuario, "El usuario no puede ser null");

        return new UsuarioEntity(
                usuario.id().value(),
                usuario.nombreCompleto().value(),
                usuario.email().value(),
                usuario.passwordHash().value(),
                usuario.createdAt(),
                usuario.updatedAt()
        );
    }

    /**
     * Reconstruye un usuario de dominio a partir de una entidad obtenida por JPA
     *
     * @param entity entidad JPA que se desea persistir
     * @return usuario de dominio reconstruido
     */
    public static Usuario toDomain(UsuarioEntity entity) {
        Objects.requireNonNull(entity, "La entidad de usuario no puede ser null");

        return Usuario.reconstitute(
                new UsuarioId(entity.getId()),
                new NombreCompleto(entity.getNombreCompleto()),
                new Email(entity.getEmail()),
                new PasswordHash(entity.getPasswordHash()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
