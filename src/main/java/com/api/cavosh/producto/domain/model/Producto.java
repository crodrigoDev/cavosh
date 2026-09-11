package com.api.cavosh.producto.domain.model;

import com.api.cavosh.producto.domain.valueobject.ProductoId;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Representa un articulo disponible en el catalogo
 */
public final class Producto {

    private final ProductoId id;
    private final Categoria categoria;
    private final String nombre;
    private final String descripcion;
    private final String imagenUrl;
    private final List<VarianteProducto> variantes;
    private final Instant createdAt;

    private Producto(
            ProductoId id,
            Categoria categoria,
            String nombre,
            String descripcion,
            String imagenUrl,
            List<VarianteProducto> variantes,
            Instant createdAt
    ) {
        this.id = Objects.requireNonNull(id, "El id no puede ser null");
        this.categoria = Objects.requireNonNull(categoria, "La categoria no puede ser null");
        this.nombre = validarNombre(nombre);
        this.descripcion = descripcion == null ? "" : descripcion.trim();
        this.imagenUrl = imagenUrl == null ? "" : imagenUrl.trim();
        this.variantes = ordenarVariantes(variantes);
        this.createdAt = Objects.requireNonNull(createdAt, "La fecha de creacion no puede ser null");
    }

    /**
     * Reconstruye un producto guardado junto con sus variantes activas
     *
     * @param id identificador del producto
     * @param categoria categoria del producto
     * @param nombre nombre visible
     * @param descripcion descripcion visible
     * @param imagenUrl ubicacion de la imagen
     * @param variantes formas disponibles de comprarlo
     * @param createdAt fecha en la que fue creado
     * @return producto reconstruido
     */
    public static Producto reconstitute(
            ProductoId id,
            Categoria categoria,
            String nombre,
            String descripcion,
            String imagenUrl,
            List<VarianteProducto> variantes,
            Instant createdAt
    ) {
        return new Producto(
                id,
                categoria,
                nombre,
                descripcion,
                imagenUrl,
                variantes,
                createdAt
        );
    }

    private static String validarNombre(String nombre) {
        Objects.requireNonNull(nombre, "El nombre no puede ser null");
        String nombreNormalizado = nombre.trim();

        if (nombreNormalizado.isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacio");

        return nombreNormalizado;
    }

    private static List<VarianteProducto> ordenarVariantes(
            List<VarianteProducto> variantes
    ) {
        Objects.requireNonNull(variantes, "Las variantes no pueden ser null");

        if (variantes.isEmpty())
            throw new IllegalArgumentException("El producto debe tener al menos una variante activa");

        return variantes.stream()
                .sorted(Comparator.comparingInt(VarianteProducto::orden))
                .toList();
    }

    public ProductoId id() {
        return id;
    }

    public Categoria categoria() {
        return categoria;
    }

    public String nombre() {
        return nombre;
    }

    public String descripcion() {
        return descripcion;
    }

    public String imagenUrl() {
        return imagenUrl;
    }

    public List<VarianteProducto> variantes() {
        return variantes;
    }

    public Instant createdAt() {
        return createdAt;
    }
}
