package com.api.cavosh.producto.application.port.in.categoria;

import com.api.cavosh.producto.domain.valueobject.CategoriaId;

/**
 * Contiene una categoria que puede mostrar el cliente
 *
 * @param id identificador de la categoria
 * @param nombre nombre visible
 * @param descripcion descripcion visible
 * @param orden posicion dentro del menu
 */
public record CategoriaResult(
        CategoriaId id,
        String nombre,
        String descripcion,
        int orden
) {
}
