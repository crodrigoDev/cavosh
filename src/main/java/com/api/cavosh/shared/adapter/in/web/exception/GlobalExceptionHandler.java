package com.api.cavosh.shared.adapter.in.web.exception;

import com.api.cavosh.shared.adapter.in.web.mapper.ValidationErrorMapper;
import com.api.cavosh.shared.adapter.in.web.response.ApiErrorResponse;
import com.api.cavosh.shared.adapter.in.web.response.ApiFieldError;
import com.api.cavosh.usuario.application.exception.EmailAlreadyRegisteredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Responde cuando se intenta registrar un email que ya pertenece a otra cuenta
     *
     * @param exception excepcion de email duplicado
     * @return respuesta HTTP 409 CONFLICT
     */
    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ApiErrorResponse> handleEmailAlreadyRegistered(
            EmailAlreadyRegisteredException exception
    ){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiErrorResponse.of(
                        HttpStatus.CONFLICT,
                        "EMAIL_ALREADY_REGISTERED",
                        exception.getMessage()
                )
        );
    }

    /**
     * Responde cuando el cuerpo HTTP no cumple las validaciones declaradas
     *
     * @param exception excepcion con los errores de validación detectados
     * @return respuesta HTTP 400 Bad Request con errores por campo
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            MethodArgumentNotValidException exception
    ){
        List<ApiFieldError> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ValidationErrorMapper::toApiFieldError)
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiErrorResponse.of(
                        HttpStatus.BAD_REQUEST,
                        "VALIDATION_ERROR",
                        "La solicitud contiene campos invalidos",
                        errors
                )
        );
    }

    /**
     * Responde cuando una regla de dominio o aplicacion recibe datos invalidos
     *
     * @param exception excepción causada por datos invalidos
     * @return respuesta HTTP 400 Bad Request
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
            IllegalArgumentException exception
    ){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiErrorResponse.of(
                        HttpStatus.BAD_REQUEST,
                        "INVALID_REQUEST",
                        exception.getMessage()
                )
        );
    }

    /**
     * Responde de forma segura ante errores no controlados
     *
     * @param exception excepcion inesperada registrada en el log del servidor
     * @return respuesta HTTP 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUnexpected(
            Exception exception
    ){
        LOGGER.error("Error no controlado por la API", exception);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiErrorResponse.of(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "INTERNAL_ERROR",
                        "Ocurrio un error inesperadoo"
                )
        );
    }
}
