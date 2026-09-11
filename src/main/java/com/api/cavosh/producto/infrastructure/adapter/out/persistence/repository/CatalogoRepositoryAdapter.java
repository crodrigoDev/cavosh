package com.api.cavosh.producto.infrastructure.adapter.out.persistence.repository;

import com.api.cavosh.producto.application.port.out.CatalogoRepository;
import com.api.cavosh.producto.domain.model.Categoria;
import com.api.cavosh.producto.domain.model.Producto;
import com.api.cavosh.producto.domain.valueobject.CategoriaId;
import com.api.cavosh.producto.domain.valueobject.ProductoId;
import com.api.cavosh.producto.infrastructure.adapter.out.persistence.entity.ProductoEntity;
import com.api.cavosh.producto.infrastructure.adapter.out.persistence.mapper.CatalogoPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Consulta el catalogo almacenado mediante Spring Data JPA
 */
@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class CatalogoRepositoryAdapter implements CatalogoRepository {

    private final CategoriaJpaRepository categoriaJpaRepository;
    private final ProductoJpaRepository productoJpaRepository;

    /**
     * Obtiene las categorias activas
     *
     * @return categorias del dominio
     */
    @Override
    public List<Categoria> findCategoriasActivas() {
        return categoriaJpaRepository
                .findAllByActivaTrueOrderByOrdenAscNombreAsc()
                .stream()
                .map(CatalogoPersistenceMapper::toDomain)
                .toList();
    }

    /**
     * Busca los productos activos
     *
     * @param categoriaId categoria elegida, si existe
     * @param texto texto normalizado de busqueda, si existe
     * @return productos encontrados
     */
    @Override
    public List<Producto> findProductos(
            CategoriaId categoriaId,
            String texto
    ) {
        return productoJpaRepository.findActivos(
                        categoriaId == null ? null : categoriaId.value(),
                        texto,
                        Sort.by("nombre").ascending()
                )
                .stream()
                .map(CatalogoPersistenceMapper::toDomain)
                .toList();
    }

    /**
     * Obtiene los productos mas recientes
     *
     * @param limite cantidad maxima solicitada
     * @return productos nuevos
     */
    @Override
    public List<Producto> findProductosNuevos(int limite) {
        return productoJpaRepository.findActivos(
                        null,
                        null,
                        Sort.by("createdAt").descending()
                )
                .stream()
                .limit(limite)
                .map(CatalogoPersistenceMapper::toDomain)
                .toList();
    }

    /**
     * Busca el detalle de un producto activo
     *
     * @param productoId identificador solicitado
     * @return producto encontrado o vacio
     */
    @Override
    public Optional<Producto> findProductoById(ProductoId productoId) {
        return productoJpaRepository.findActivoById(productoId.value())
                .filter(producto -> producto.getVariantes()
                        .stream()
                        .anyMatch(variante -> variante.isActiva()))
                .map(CatalogoPersistenceMapper::toDomain);
    }

}
