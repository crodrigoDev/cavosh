package com.api.cavosh.shared.adapter.in.web.mapper;

import com.api.cavosh.shared.adapter.in.web.response.ApiFieldError;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationErrorMapper {

    /**
     * Convierte un error de validacion de Spring en un error de campo de la API
     *
     * @param fieldError error de validacion entregado por Spring
     * @return error de campo serializable para el cliente
     */
    public static ApiFieldError toApiFieldError(FieldError fieldError) {
        return new ApiFieldError(
                normalizeFieldName(fieldError.getField()),
                fieldError.getDefaultMessage()
        );
    }

    /**
     * Ajusta nombres internos de propiedades para que sean comprensibles por el cliente
     *
     * @param fieldName nombre interno del campo validado
     * @return nombre de campo expuesto por la API
     */
    private static String normalizeFieldName(String fieldName) {
        if ("passwordConfirmationValid".equals(fieldName)) {
            return "passwordConfirmation";
        }

        return fieldName;
    }
}
