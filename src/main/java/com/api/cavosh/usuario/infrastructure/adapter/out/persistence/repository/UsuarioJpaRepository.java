package com.api.cavosh.usuario.infrastructure.adapter.out.persistence.repository;

import com.api.cavosh.usuario.infrastructure.adapter.out.persistence.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio de Spring Data JPA para consultar y persistir entidades de usuario
 */
public interface UsuarioJpaRepository
        extends JpaRepository<UsuarioEntity, UUID> {

    /**
     * Determina si existe una entidad de usuario con el email indicado
     *
     * @param email email normalizado que se desea consultar
     * @return {@code true} si existe un usuario con ese email
     */
    boolean existsByEmail(String email);

    /**
     * Busca una entidad de usuario a partir de su email
     *
     * @param email email normalizado del usuario
     * @return la entidad encontrada, o vacio si no existe
     */
    Optional<UsuarioEntity> findByEmail(String email);
}
