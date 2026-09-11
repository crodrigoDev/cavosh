package com.api.cavosh.producto.infrastructure.adapter.out.persistence.repository;

import com.api.cavosh.producto.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Permite consultar productos mediante Spring Data JPA
 */
public interface ProductoJpaRepository
        extends JpaRepository<ProductoEntity, UUID> {

    /**
     * Busca productos visibles por categoria y texto opcionales
     *
     * @param categoriaId identificador de categoria, si existe
     * @param texto texto normalizado, si existe
     * @param sort orden solicitado
     * @return entidades encontradas
     */
    @EntityGraph(attributePaths = "categoria")
    @Query("""
            SELECT p
            FROM ProductoEntity p
            WHERE p.activo = true
              AND p.categoria.activa = true
              AND (:categoriaId IS NULL OR p.categoria.id = :categoriaId)
              AND (
                    :texto IS NULL
                    OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))
                    OR LOWER(p.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))
              )
              AND EXISTS (
                    SELECT v.id
                    FROM VarianteProductoEntity v
                    WHERE v.producto = p
                      AND v.activa = true
              )
            """)
    List<ProductoEntity> findActivos(
            @Param("categoriaId") UUID categoriaId,
            @Param("texto") String texto,
            Sort sort
    );

    /**
     * Busca el detalle de un producto visible
     *
     * @param id identificador del producto
     * @return producto encontrado o vacio
     */
    @EntityGraph(attributePaths = {"categoria", "variantes"})
    @Query("""
            SELECT DISTINCT p
            FROM ProductoEntity p
            WHERE p.id = :id
              AND p.activo = true
              AND p.categoria.activa = true
            """)
    Optional<ProductoEntity> findActivoById(@Param("id") UUID id);
}
