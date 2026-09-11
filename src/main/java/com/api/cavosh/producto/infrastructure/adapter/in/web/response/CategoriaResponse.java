package com.api.cavosh.producto.infrastructure.adapter.in.web.response;

import java.util.UUID;

/**
 * Representa una categoria mostrada en el menu
 *
 * @param id identificador de la categoria
 * @param nombre nombre visible
 * @param descripcion descripcion visible
 * @param orden posicion dentro del menu
 */
public record CategoriaResponse(
        UUID id,
        String nombre,
        String descripcion,
        int orden
) {
}
