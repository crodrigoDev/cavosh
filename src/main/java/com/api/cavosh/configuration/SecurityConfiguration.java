package com.api.cavosh.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configura componentes compartidos relacionados con seguridad
 */
@Configuration
public class SecurityConfiguration {

    /**
     * Crea el encoder de BCrypt utilizado para proteger las contraseñas
     *
     * @return encoder de contraseñas basado en BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
