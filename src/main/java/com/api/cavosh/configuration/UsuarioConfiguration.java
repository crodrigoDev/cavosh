package com.api.cavosh.configuration;

import com.api.cavosh.usuario.application.port.in.RegistrarUsuarioUseCase;
import com.api.cavosh.usuario.application.port.out.PasswordHasher;
import com.api.cavosh.usuario.application.port.out.UsuarioRepository;
import com.api.cavosh.usuario.application.service.RegistrarUsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Configura los caso de uso relacionados con usuarios
 */
@Configuration
public class UsuarioConfiguration {

    /**
     * Crea el reloj compartido utilizado por los casos de uso
     *
     * @return reloj basado en la zona horaria UTC
     */
    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    /**
     * Crea el caso de uso de registro de usuarios con sus puertos de salida
     *
     * @param usuarioRepository puerto de persistencia de usuarios
     * @param passwordHasher puerto para proteger las contraseñas
     * @param clock reloj utilizado para asignar fechas al usuario
     * @return caso de uso de registro listo para recibir solicitudes
     */
    @Bean
    public RegistrarUsuarioUseCase registrarUsuarioUseCase(
            UsuarioRepository usuarioRepository,
            PasswordHasher passwordHasher,
            Clock clock
    ) {
        return new RegistrarUsuarioService(
                usuarioRepository,
                passwordHasher,
                clock
        );
    }
}
