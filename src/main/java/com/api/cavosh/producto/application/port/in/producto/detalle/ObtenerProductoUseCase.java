package com.api.cavosh.producto.application.port.in.producto.detalle;

import com.api.cavosh.producto.domain.valueobject.ProductoId;

/**
 * Permite consultar el detalle de un producto
 */
public interface ObtenerProductoUseCase {

    /**
     * Obtiene el producto indicado
     *
     * @param productoId identificador solicitado
     * @return detalle del producto
     */
    ProductoDetalleResult ejecutar(ProductoId productoId);
}
