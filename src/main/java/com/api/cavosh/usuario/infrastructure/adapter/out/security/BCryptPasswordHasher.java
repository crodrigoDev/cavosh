package com.api.cavosh.usuario.infrastructure.adapter.out.security;

import com.api.cavosh.usuario.application.port.out.PasswordHasher;
import com.api.cavosh.usuario.domain.valueobject.PasswordHash;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Implementa el puerto de hash de contraseñas mediante BCrypt de Spring Security
 */
@Component
public class BCryptPasswordHasher implements PasswordHasher {

    private final PasswordEncoder passwordEncoder;

    /**
     * Crea el adaptador con el encoder de contraseñas configurado
     *
     * @param passwordEncoder encoder utilizado para generar hashes de BCrypt
     */
    public BCryptPasswordHasher(
            PasswordEncoder passwordEncoder
    ){
        this.passwordEncoder = Objects.requireNonNull(
                passwordEncoder,
                "El encoder de contraseñas no puede ser null"
        );
    }

    /**
     * Genera un hash BCrypt a partir de una contraseña en texto plano
     *
     * @param password contraseña en texto plano
     * @return hash seguro apto para persistir
     */
    @Override
    public PasswordHash hash(String password) {
        return new PasswordHash(
                passwordEncoder.encode(password)
        );
    }

    /**
     * Comprueba si una contraseña corresponde al hash almacenado
     *
     * @param password contraseña recibida durante el login
     * @param passwordHash hash almacenado del usuario
     * @return {@code true} cuando la contraseña es correcta
     */
    @Override
    public boolean matches(String password, PasswordHash passwordHash) {
        return passwordEncoder.matches(
                password,
                passwordHash.value()
        );
    }
}
