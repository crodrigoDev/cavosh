package com.api.cavosh.shared.adapter.in.web.response;

import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String code,
        String message,
        List<ApiFieldError> errors
) {

    /**
     * Crea una respuesta de error sin errores asociados a campos especificos
     *
     * @param status estado HTTP del error
     * @param code codigo estable del error
     * @param message mensaje descriptivo del error
     * @return respuesta uniforme de error
     */
    public static ApiErrorResponse of(
            HttpStatus status,
            String code,
            String message
    ){
        return new ApiErrorResponse(
                Instant.now(),
                status.value(),
                code,
                message,
                List.of()
        );
    }

    /**}
     * Crea una respuesta de error con errores asociados a campos especificos
     *
     * @param status estado HTTP del error
     * @param code codigo estable del error
     * @param message mensaje descriptivo del error
     * @param errors errores de validación por campo
     * @return respuesta uniforme de error
     */
    public static ApiErrorResponse of(
            HttpStatus status,
            String code,
            String message,
            List<ApiFieldError> errors
    ){
        return new ApiErrorResponse(
                Instant.now(),
                status.value(),
                code,
                message,
                List.copyOf(errors)
        );
    }
}
