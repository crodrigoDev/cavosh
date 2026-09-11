package com.api.cavosh.configuration;

import com.api.cavosh.producto.application.port.out.CatalogoRepository;
import com.api.cavosh.producto.application.service.CatalogoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configura los casos de uso del catalogo
 */
@Configuration
public class ProductoConfiguration {

    /**
     * Crea el servicio compartido por las consultas del catalogo
     *
     * @param catalogoRepository repositorio del catalogo
     * @return servicio que implementa las consultas del catalogo
     */
    @Bean
    public CatalogoService catalogoService(
            CatalogoRepository catalogoRepository
    ) {
        return new CatalogoService(catalogoRepository);
    }
}
