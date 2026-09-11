package com.api.cavosh.producto.application.port.in.producto.nuevo;

import com.api.cavosh.producto.application.port.in.producto.ProductoResumenResult;

import java.util.List;

/**
 * Permite obtener los productos agregados recientemente
 */
public interface ListarProductosNuevosUseCase {

    /**
     * Obtiene una cantidad limitada de productos nuevos
     *
     * @param limite cantidad maxima solicitada
     * @return productos nuevos
     */
    List<ProductoResumenResult> ejecutar(int limite);
}
