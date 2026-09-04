package com.api.cavosh.shared.web.response;

import org.springframework.http.HttpStatus;

import java.time.Instant;

/**
 * Representa una respuesta HTTP exitosa y uniforme de la API
 *
 * @param timestamp instante en el que se creo la respuesta
 * @param status código de estado HTTP
 * @param message mensaje descriptivo para el cliente
 * @param data datos devueltos por el endpoint
 * @param <T> tipo de los datos devueltos
 */
public record ApiResponse<T>(
        Instant timestamp,
        int status,
        String message,
        T data
) {

    /**
     * Crea una respuesta exitosa con el estado HTTP indicado
     *
     * @param status estado HTTP de la respuesta
     * @param message mensaje descriptivo para el cliente
     * @param data datos que devuelve el endpoint
     * @param <T> tipo de los datos envueltos
     * @return respuesta API uniforme
     */
    public static <T> ApiResponse<T> success(
            HttpStatus status,
            String message,
            T data
    ){
        return new ApiResponse<>(
                Instant.now(),
                status.value(),
                message,
                data
        );
    }
}
