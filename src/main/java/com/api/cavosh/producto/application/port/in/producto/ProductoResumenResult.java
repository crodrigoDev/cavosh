package com.api.cavosh.producto.application.port.in.producto;

import com.api.cavosh.producto.application.port.in.categoria.CategoriaResult;
import com.api.cavosh.producto.domain.valueobject.ProductoId;

import java.math.BigDecimal;

/**
 * Contiene los datos necesarios para mostrar un producto en una lista
 *
 * @param id identificador del producto
 * @param categoria categoria a la que pertenece
 * @param nombre nombre visible
 * @param descripcion descripcion corta
 * @param imagenUrl ubicacion de la imagen
 * @param precioDesde menor precio disponible
 * @param moneda codigo de moneda
 */
public record ProductoResumenResult(
        ProductoId id,
        CategoriaResult categoria,
        String nombre,
        String descripcion,
        String imagenUrl,
        BigDecimal precioDesde,
        String moneda
) {
}
