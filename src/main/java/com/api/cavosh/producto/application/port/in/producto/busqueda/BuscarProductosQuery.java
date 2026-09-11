package com.api.cavosh.producto.application.port.in.producto.busqueda;

import com.api.cavosh.producto.domain.valueobject.CategoriaId;

/**
 * Agrupa los filtros usados para buscar productos
 *
 * @param categoriaId categoria elegida, si existe
 * @param texto texto escrito por el usuario, si existe
 */
public record BuscarProductosQuery(
        CategoriaId categoriaId,
        String texto
) {
}
