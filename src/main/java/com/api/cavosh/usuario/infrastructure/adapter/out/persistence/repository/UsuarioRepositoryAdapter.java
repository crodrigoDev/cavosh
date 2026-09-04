package com.api.cavosh.usuario.infrastructure.adapter.out.persistence.repository;

import com.api.cavosh.usuario.domain.model.Usuario;
import com.api.cavosh.usuario.application.port.out.UsuarioRepository;
import com.api.cavosh.usuario.domain.valueobject.Email;
import com.api.cavosh.usuario.domain.valueobject.UsuarioId;
import com.api.cavosh.usuario.infrastructure.adapter.out.persistence.mapper.UsuarioPersistenceMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementa el repositorio de dominio de usuarios mediante Spring Data JPA
 */
@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final UsuarioJpaRepository usuarioJpaRepository;

    /**
     * Crea el adaptador con el repositorio JPA requerido
     *
     * @param usuarioJpaRepository repositorio JPA de usuarios
     */
    public UsuarioRepositoryAdapter(
            UsuarioJpaRepository usuarioJpaRepository
    ){
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    /**
     * Determina si existe un usuario con el email indicado
     *
     * @param email email normalizado que desea consultar
     * @return {@code true} si existe un usuario con ese email
     */
    @Override
    public boolean existsByEmail(Email email) {
        return usuarioJpaRepository.existsByEmail(email.value());
    }

    /**
     * Busca un usuario de dominio a partir de su identificador
     * @param id identificador del Usuario
     * @return el usuario encontrado, o vacio si no existe
     */
    @Override
    public Optional<Usuario> findById(UsuarioId id) {
        return usuarioJpaRepository.findById(id.value())
                .map(UsuarioPersistenceMapper::toDomain);
    }

    /**
     * Busca un usuario de dominio a partir de su email
     *
     * @param email email normalizado del Usuario
     * @return el usuario encontrado, o vacío si no existe
     */
    @Override
    public Optional<Usuario> findByEmail(Email email) {
        return usuarioJpaRepository.findByEmail(email.value())
                .map(UsuarioPersistenceMapper::toDomain);
    }

    /**
     * Persiste un usuario de dominio y devuelve su versión persistida
     *
     * @param usuario usuario que desea persistir
     * @return usuario persistido y reconstruido como objeto de dominio
     */
    @Override
    public Usuario save(Usuario usuario) {
        return UsuarioPersistenceMapper.toDomain(
                usuarioJpaRepository.save(
                        UsuarioPersistenceMapper.toEntity(usuario)
                )
        );
    }
}
