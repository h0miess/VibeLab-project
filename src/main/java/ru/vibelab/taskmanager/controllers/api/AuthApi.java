package ru.vibelab.taskmanager.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vibelab.taskmanager.models.requests.AuthenticationRequest;
import ru.vibelab.taskmanager.models.requests.RegisterRequest;
import ru.vibelab.taskmanager.models.responses.AuthenticationResponse;
import ru.vibelab.taskmanager.models.responses.ErrorResponse;

@Tag(
        name = "Регистрация и аутентификация",
        description = "AuthController"
)
@RequestMapping("/api/v1/auth")
public interface AuthApi {

    @Operation(
            summary = "Метод для аутентификации",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Аутентификация прошла успешно",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = AuthenticationResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не аутентифицирован / Некорректный токен",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE
                                    )
                    }
                    )

            }
    )
    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> login (@RequestBody @Valid AuthenticationRequest request);

    @Operation(
            summary = "Метод для регистрации",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Регистрация прошла успешно"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользователь не зарегистрирован",
                            content = {
                                    @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE
                                    )
                            }

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка при регистрации",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )


            }
    )
    @PostMapping("/register")
    ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request);

    @Operation(
            summary = "Метод для подтверждения регистрации",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Подтверждение прошло успешно",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = AuthenticationResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка при подтверждении",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка при подтверждении",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )


            }
    )
    @GetMapping("/register/confirm")
    ResponseEntity<AuthenticationResponse> confirm(@RequestParam("token") String token);
}