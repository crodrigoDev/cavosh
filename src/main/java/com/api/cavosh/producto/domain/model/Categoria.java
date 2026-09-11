package com.api.cavosh.producto.domain.model;

import com.api.cavosh.producto.domain.valueobject.CategoriaId;

import java.util.Objects;

/**
 * Representa una categoria usada para ordenar el catalogo
 */
public final class Categoria {

    private final CategoriaId id;
    private final String nombre;
    private final String descripcion;
    private final int orden;

    private Categoria(
            CategoriaId id,
            String nombre,
            String descripcion,
            int orden
    ) {
        this.id = Objects.requireNonNull(id, "El id no puede ser null");
        this.nombre = validarNombre(nombre);
        this.descripcion = descripcion == null ? "" : descripcion.trim();

        if (orden < 0)
            throw new IllegalArgumentException("El orden no puede ser negativo");

        this.orden = orden;
    }

    /**
     * Reconstruye una categoria guardada
     *
     * @param id identificador de la categoria
     * @param nombre nombre visible
     * @param descripcion descripcion visible
     * @param orden posicion dentro del menu
     * @return categoria reconstruida
     */
    public static Categoria reconstitute(
            CategoriaId id,
            String nombre,
            String descripcion,
            int orden
    ) {
        return new Categoria(id, nombre, descripcion, orden);
    }

    private static String validarNombre(String nombre) {
        Objects.requireNonNull(nombre, "El nombre no puede ser null");
        String nombreNormalizado = nombre.trim();

        if (nombreNormalizado.isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacio");

        return nombreNormalizado;
    }

    public CategoriaId id() {
        return id;
    }

    public String nombre() {
        return nombre;
    }

    public String descripcion() {
        return descripcion;
    }

    public int orden() {
        return orden;
    }
}
