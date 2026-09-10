package com.api.cavosh.usuario.infrastructure.adapter.in.web.mapper;

import com.api.cavosh.usuario.application.port.in.login.LoginCommand;
import com.api.cavosh.usuario.application.port.in.login.LoginResult;
import com.api.cavosh.usuario.application.port.in.registro.RegistrarUsuarioResult;
import com.api.cavosh.usuario.domain.valueobject.UsuarioId;
import com.api.cavosh.usuario.infrastructure.adapter.in.web.request.LoginRequest;
import com.api.cavosh.usuario.infrastructure.adapter.in.web.response.LoginResponse;
import com.api.cavosh.usuario.infrastructure.adapter.in.web.response.RegistrarUsuarioResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
     * Convierte la solicitud de login en los datos que recibe el caso de uso.
     *
     * @param request datos recibidos por HTTP
     * @return datos para ejecutar el login
     */
    LoginCommand toCommand(LoginRequest request);

    /**
     * Convierte el resultado del login en la respuesta para el cliente.
     *
     * @param result resultado del inicio de sesion
     * @return respuesta con el usuario y su token
     */
    @Mapping(target = "tokenType", constant = "Bearer")
    LoginResponse toResponse(LoginResult result);

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
