package com.api.cavosh.producto.infrastructure.adapter.out.persistence.repository;

import com.api.cavosh.producto.infrastructure.adapter.out.persistence.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Permite consultar categorias mediante Spring Data JPA
 */
public interface CategoriaJpaRepository
        extends JpaRepository<CategoriaEntity, UUID> {

    /**
     * Obtiene las categorias activas en el orden del menu
     *
     * @return categorias visibles
     */
    List<CategoriaEntity> findAllByActivaTrueOrderByOrdenAscNombreAsc();
}
