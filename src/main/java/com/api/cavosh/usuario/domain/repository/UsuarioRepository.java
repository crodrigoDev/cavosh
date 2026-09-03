package com.api.cavosh.usuario.domain.repository;

import com.api.cavosh.usuario.domain.model.Usuario;
import com.api.cavosh.usuario.domain.valueobject.Email;
import com.api.cavosh.usuario.domain.valueobject.UsuarioId;

import java.util.Optional;

/**
 * Define las operaciones de persistencia del agregado {@link Usuario}
 */
public interface UsuarioRepository {

    /**
     * Determina si existe un Usuario registrado con el email indicado
     *
     * @param email email normalizado que desea consultar
     * @return {@code true} si el email le pertenece a un Usuario
     */
    boolean existsByEmail(Email email);

    /**
     * Busca un Usuario a partir de su Id
     *
     * @param id identidad del Usuario
     * @return el Usuario encontrado o vacio si no existe
     */
    Optional<Usuario> findById(UsuarioId id);

    /**
     * Busca un Usuario a partir de su Email
     *
     * @param email email normalizado del Usuario
     * @return el Usuario encontrado o vacio si no existe
     */
    Optional<Usuario> findByEmail(Email email);

    /**
     * Guarda un Usuario nuevo o actualiza uno existente
     *
     * @param usuario usuario que desea persistir
     * @return usuario persistido
     */
    Usuario save(Usuario usuario);
}
