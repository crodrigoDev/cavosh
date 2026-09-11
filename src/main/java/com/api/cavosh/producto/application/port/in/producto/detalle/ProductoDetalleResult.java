package com.api.cavosh.producto.application.port.in.producto.detalle;

import com.api.cavosh.producto.application.port.in.categoria.CategoriaResult;
import com.api.cavosh.producto.domain.valueobject.ProductoId;

import java.util.List;

/**
 * Contiene toda la informacion visible de un producto
 *
 * @param id identificador del producto
 * @param categoria categoria a la que pertenece
 * @param nombre nombre visible
 * @param descripcion descripcion completa
 * @param imagenUrl ubicacion de la imagen
 * @param variantes opciones disponibles para comprarlo
 */
public record ProductoDetalleResult(
        ProductoId id,
        CategoriaResult categoria,
        String nombre,
        String descripcion,
        String imagenUrl,
        List<VarianteProductoResult> variantes
) {

    /**
     * Protege la lista recibida para que no cambie desde fuera
     */
    public ProductoDetalleResult {
        variantes = List.copyOf(variantes);
    }
}
