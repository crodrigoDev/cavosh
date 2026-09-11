package com.api.cavosh.producto.application.exception;

/**
 * Indica que el producto solicitado no existe o no esta disponible
 */
public class ProductoNotFoundException extends RuntimeException {

    /**
     * Crea el error con un mensaje seguro para el cliente
     */
    public ProductoNotFoundException() {
        super("El producto solicitado no fue encontrado");
    }
}
