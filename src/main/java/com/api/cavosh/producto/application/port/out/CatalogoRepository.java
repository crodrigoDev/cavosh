package com.api.cavosh.producto.application.port.out;

import com.api.cavosh.producto.domain.model.Categoria;
import com.api.cavosh.producto.domain.model.Producto;
import com.api.cavosh.producto.domain.valueobject.CategoriaId;
import com.api.cavosh.producto.domain.valueobject.ProductoId;

import java.util.List;
import java.util.Optional;

/**
 * Define las consultas que necesita el catalogo
 */
public interface CatalogoRepository {

    /**
     * Obtiene las categorias activas en su orden de presentacion
     *
     * @return categorias visibles
     */
    List<Categoria> findCategoriasActivas();

    /**
     * Busca productos activos usando filtros opcionales
     *
     * @param categoriaId categoria elegida, si existe
     * @param texto texto normalizado de busqueda, si existe
     * @return productos encontrados
     */
    List<Producto> findProductos(
            CategoriaId categoriaId,
            String texto
    );

    /**
     * Obtiene los productos creados recientemente
     *
     * @param limite cantidad maxima solicitada
     * @return productos nuevos
     */
    List<Producto> findProductosNuevos(int limite);

    /**
     * Busca un producto activo por su identificador
     *
     * @param productoId identificador solicitado
     * @return producto encontrado o vacio
     */
    Optional<Producto> findProductoById(ProductoId productoId);
}
