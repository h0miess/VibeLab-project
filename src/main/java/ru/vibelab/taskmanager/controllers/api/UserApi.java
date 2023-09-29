package ru.vibelab.taskmanager.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vibelab.taskmanager.models.requests.PasswordRequest;
import ru.vibelab.taskmanager.models.responses.ErrorResponse;
import ru.vibelab.taskmanager.models.dto.UserDto;
import ru.vibelab.taskmanager.models.responses.PasswordResponse;

import java.security.Principal;

@Tag(
        name = "Профиль",
        description = "UserController"
)
@ApiResponse(
        responseCode = "401",
        description = "Пользователь не аутентифицирован",
        content = @Content
)
@SecurityRequirement(name = "bearerAuth")
public interface UserApi {



    @Operation(
            summary = "Метод для изменения профиля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Профиль успешно изменен",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = UserDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка при изменении данных",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )
            }
    )
    @PatchMapping("/user")
    ResponseEntity<UserDto> updateProfile(@Parameter(hidden = true)Principal principal, @RequestBody UserDto request);
    @Operation(
            summary = "Метод для удаления профиля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Профиль успешно удален",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = UserDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )
            }
    )
    @DeleteMapping("/user")
    ResponseEntity<UserDto> deleteProfile(@Parameter(hidden = true) Principal principal);
    @Operation(
            summary = "Метод для изменения пароля",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пароль успешно изменен",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = PasswordResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка при изменении пароля",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }

                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )
            }
    )
    @PatchMapping("/user/password")
    ResponseEntity<PasswordResponse> updatePassword(@Parameter(hidden = true) Principal principal, @RequestBody PasswordRequest request);
}
