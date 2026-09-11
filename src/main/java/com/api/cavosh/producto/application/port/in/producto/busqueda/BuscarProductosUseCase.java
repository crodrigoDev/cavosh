package com.api.cavosh.producto.application.port.in.producto.busqueda;

import com.api.cavosh.producto.application.port.in.producto.ProductoResumenResult;

import java.util.List;

/**
 * Permite buscar los productos visibles
 */
public interface BuscarProductosUseCase {

    /**
     * Busca productos usando los filtros recibidos
     *
     * @param query filtros solicitados
     * @return productos encontrados
     */
    List<ProductoResumenResult> ejecutar(BuscarProductosQuery query);
}
