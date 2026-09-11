package com.api.cavosh.producto.infrastructure.adapter.out.persistence.mapper;

import com.api.cavosh.producto.domain.model.Categoria;
import com.api.cavosh.producto.domain.model.Producto;
import com.api.cavosh.producto.domain.model.VarianteProducto;
import com.api.cavosh.producto.domain.valueobject.CategoriaId;
import com.api.cavosh.producto.domain.valueobject.Precio;
import com.api.cavosh.producto.domain.valueobject.ProductoId;
import com.api.cavosh.producto.domain.valueobject.VarianteProductoId;
import com.api.cavosh.producto.infrastructure.adapter.out.persistence.entity.CategoriaEntity;
import com.api.cavosh.producto.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import com.api.cavosh.producto.infrastructure.adapter.out.persistence.entity.VarianteProductoEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Convierte los datos guardados en objetos del catalogo
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CatalogoPersistenceMapper {

    /**
     * Reconstruye una categoria desde su entidad
     *
     * @param entity categoria guardada
     * @return categoria del dominio
     */
    public static Categoria toDomain(CategoriaEntity entity) {
        Objects.requireNonNull(entity, "La categoria no puede ser null");

        return Categoria.reconstitute(
                new CategoriaId(entity.getId()),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getOrden()
        );
    }

    /**
     * Reconstruye un producto junto con sus variantes activas
     *
     * @param entity producto guardado
     * @return producto del dominio
     */
    public static Producto toDomain(ProductoEntity entity) {
        Objects.requireNonNull(entity, "El producto no puede ser null");

        return Producto.reconstitute(
                new ProductoId(entity.getId()),
                toDomain(entity.getCategoria()),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getImagenUrl(),
                entity.getVariantes().stream()
                        .filter(VarianteProductoEntity::isActiva)
                        .map(CatalogoPersistenceMapper::toDomain)
                        .toList(),
                entity.getCreatedAt()
        );
    }

    private static VarianteProducto toDomain(
            VarianteProductoEntity entity
    ) {
        return VarianteProducto.reconstitute(
                new VarianteProductoId(entity.getId()),
                entity.getNombre(),
                new Precio(entity.getPrecio(), entity.getMoneda()),
                entity.getOrden()
        );
    }
}
