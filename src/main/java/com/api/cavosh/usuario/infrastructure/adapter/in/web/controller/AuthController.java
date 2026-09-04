package com.api.cavosh.usuario.infrastructure.adapter.in.web.controller;

import com.api.cavosh.shared.adapter.in.web.response.ApiResponse;
import com.api.cavosh.usuario.application.command.RegistrarUsuarioCommand;
import com.api.cavosh.usuario.application.port.in.RegistrarUsuarioUseCase;
import com.api.cavosh.usuario.application.result.RegistrarUsuarioResult;
import com.api.cavosh.usuario.infrastructure.adapter.in.web.mapper.UsuarioWebMapper;
import com.api.cavosh.usuario.infrastructure.adapter.in.web.request.RegistrarUsuarioRequest;
import com.api.cavosh.usuario.infrastructure.adapter.in.web.response.RegistrarUsuarioResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Expone los endpoints HTTP relacionados con autenticacion de usuarios
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase; //caso de uso de registro
    private final UsuarioWebMapper usuarioWebMapper; // mapper de resultados de aplicacion a respuestas HTTP

    /**
     * Registra un usuario nuevo a partir de los datos enviados por HTTP
     *
     * @param request datos validos del registro
     * @return respuesta HTTP con los datos seguros del usuario registrado
     */
    @PostMapping("/registrar")
    public ResponseEntity<ApiResponse<RegistrarUsuarioResponse>> registrar(
            @Valid @RequestBody RegistrarUsuarioRequest request
    ){
        RegistrarUsuarioResult result = registrarUsuarioUseCase.ejecutar(
                new RegistrarUsuarioCommand(
                        request.nombreCompleto(),
                        request.email(),
                        request.password()
                )
        );

        RegistrarUsuarioResponse response =
                usuarioWebMapper.toResponse(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success(
                        HttpStatus.CREATED,
                        "Usuario registrado correctamente",
                        response
                )
        );
    }
}
