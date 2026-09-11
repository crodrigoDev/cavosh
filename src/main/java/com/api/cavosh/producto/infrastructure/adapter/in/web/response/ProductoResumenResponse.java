package com.api.cavosh.producto.infrastructure.adapter.in.web.response;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Representa un producto mostrado dentro de una lista
 *
 * @param id identificador del producto
 * @param categoria categoria a la que pertenece
 * @param nombre nombre visible
 * @param descripcion descripcion corta
 * @param imagenUrl ubicacion de la imagen
 * @param precioDesde menor precio disponible
 * @param moneda codigo de moneda
 */
public record ProductoResumenResponse(
        UUID id,
        CategoriaResponse categoria,
        String nombre,
        String descripcion,
        String imagenUrl,
        BigDecimal precioDesde,
        String moneda
) {
}
