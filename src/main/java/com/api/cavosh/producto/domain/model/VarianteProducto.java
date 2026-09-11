package com.api.cavosh.producto.domain.model;

import com.api.cavosh.producto.domain.valueobject.Precio;
import com.api.cavosh.producto.domain.valueobject.VarianteProductoId;

import java.util.Objects;

/**
 * Representa una forma disponible de comprar un producto
 */
public final class VarianteProducto {

    private final VarianteProductoId id;
    private final String nombre;
    private final Precio precio;
    private final int orden;

    private VarianteProducto(
            VarianteProductoId id,
            String nombre,
            Precio precio,
            int orden
    ) {
        this.id = Objects.requireNonNull(id, "El id no puede ser null");
        this.nombre = validarNombre(nombre);
        this.precio = Objects.requireNonNull(precio, "El precio no puede ser null");

        if (orden < 0)
            throw new IllegalArgumentException("El orden no puede ser negativo");

        this.orden = orden;
    }

    /**
     * Reconstruye una variante guardada
     *
     * @param id identificador de la variante
     * @param nombre nombre visible como Pequeño o Unidad
     * @param precio precio de venta
     * @param orden posicion dentro del producto
     * @return variante reconstruida
     */
    public static VarianteProducto reconstitute(
            VarianteProductoId id,
            String nombre,
            Precio precio,
            int orden
    ) {
        return new VarianteProducto(id, nombre, precio, orden);
    }

    private static String validarNombre(String nombre) {
        Objects.requireNonNull(nombre, "El nombre no puede ser null");
        String nombreNormalizado = nombre.trim();

        if (nombreNormalizado.isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacio");

        return nombreNormalizado;
    }

    public VarianteProductoId id() {
        return id;
    }

    public String nombre() {
        return nombre;
    }

    public Precio precio() {
        return precio;
    }

    public int orden() {
        return orden;
    }
}
