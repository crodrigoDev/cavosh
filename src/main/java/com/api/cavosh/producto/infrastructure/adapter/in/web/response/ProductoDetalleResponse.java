package com.api.cavosh.producto.infrastructure.adapter.in.web.response;

import java.util.List;
import java.util.UUID;

/**
 * Representa el detalle completo de un producto
 *
 * @param id identificador del producto
 * @param categoria categoria a la que pertenece
 * @param nombre nombre visible
 * @param descripcion descripcion completa
 * @param imagenUrl ubicacion de la imagen
 * @param variantes opciones disponibles para comprarlo
 */
public record ProductoDetalleResponse(
        UUID id,
        CategoriaResponse categoria,
        String nombre,
        String descripcion,
        String imagenUrl,
        List<VarianteProductoResponse> variantes
) {

    /**
     * Protege la lista recibida para que no cambie desde fuera
     */
    public ProductoDetalleResponse {
        variantes = List.copyOf(variantes);
    }
}
