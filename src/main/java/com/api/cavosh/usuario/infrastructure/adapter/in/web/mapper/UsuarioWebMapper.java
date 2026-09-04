package com.api.cavosh.usuario.infrastructure.adapter.in.web.mapper;

import com.api.cavosh.usuario.application.result.RegistrarUsuarioResult;
import com.api.cavosh.usuario.domain.valueobject.UsuarioId;
import com.api.cavosh.usuario.infrastructure.adapter.in.web.response.RegistrarUsuarioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

/**
 * Convierte resultados de la aplicacion en respuestas HTTP de usuario
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UsuarioWebMapper {

    /**
     * Convierte el resultado de registro en la respuesta expuesta por HTTP
     *
     * @param result resultado seguro del caso de uso de registro
     * @return respuesta HTTP con los datos del usuario registrado
     */
    RegistrarUsuarioResponse toResponse(RegistrarUsuarioResult result);

    /**
     * Convierte un identificador de dominio en un UUID apto para la respuesta HTTP
     *
     * @param usuarioId identificador de dominio del usuario
     * @return UUID del usuario, o {@code null} si no se recibio identificador
     */
    default UUID toUuid(UsuarioId usuarioId) {
        return usuarioId == null ? null : usuarioId.value();
    }
}
