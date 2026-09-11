package com.api.cavosh.producto.application.port.in.categoria;

import java.util.List;

/**
 * Permite consultar las categorias visibles del catalogo
 */
public interface ListarCategoriasUseCase {

    /**
     * Obtiene todas las categorias activas
     *
     * @return categorias ordenadas
     */
    List<CategoriaResult> ejecutar();
}
