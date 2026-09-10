package com.api.cavosh.usuario.infrastructure.adapter.in.web.controller;

import com.api.cavosh.shared.adapter.in.web.response.ApiResponse;
import com.api.cavosh.usuario.application.port.in.login.LoginCommand;
import com.api.cavosh.usuario.application.port.in.login.LoginResult;
import com.api.cavosh.usuario.application.port.in.login.LoginUseCase;
import com.api.cavosh.usuario.application.port.in.registro.RegistrarUsuarioCommand;
import com.api.cavosh.usuario.application.port.in.registro.RegistrarUsuarioUseCase;
import com.api.cavosh.usuario.application.port.in.registro.RegistrarUsuarioResult;
import com.api.cavosh.usuario.infrastructure.adapter.in.web.mapper.UsuarioWebMapper;
import com.api.cavosh.usuario.infrastructure.adapter.in.web.request.LoginRequest;
import com.api.cavosh.usuario.infrastructure.adapter.in.web.request.RegistrarUsuarioRequest;
import com.api.cavosh.usuario.infrastructure.adapter.in.web.response.LoginResponse;
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
    private final LoginUseCase loginUseCase; // caso de uso de login
    private final UsuarioWebMapper usuarioWebMapper; // mapper de resultados de aplicacion a respuestas HTTP

    /**
     * Inicia sesion con el email y la contraseña recibidos.
     *
     * @param request datos enviados para iniciar sesion
     * @return respuesta con el usuario y su access token
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid @RequestBody LoginRequest request
    ) {
        LoginCommand command = usuarioWebMapper.toCommand(request);
        LoginResult result = loginUseCase.ejecutar(command);
        LoginResponse response = usuarioWebMapper.toResponse(result);

        return ResponseEntity.ok(
                ApiResponse.success(
                        HttpStatus.OK,
                        "Inicio de sesión correcto",
                        response
                )
        );
    }

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
