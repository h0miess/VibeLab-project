package ru.vibelab.taskmanager.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vibelab.taskmanager.models.requests.ColumnRequest;
import ru.vibelab.taskmanager.models.responses.ColumnResponse;
import ru.vibelab.taskmanager.models.responses.ErrorResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateColumnRequest;

import java.security.Principal;

@Tag(
        name = "Работа со столбцами",
        description = "ColumnController"
)
@ApiResponse(
        responseCode = "401",
        description = "Пользователь не аутентифицирован",
        content = @Content
)
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/columns")
public interface ColumnApi {

    @Operation(
            summary = "Метод для создания столбца",
            parameters = {
                    @Parameter(
                            name = "boardId",
                            description = "ID доски",
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "Long")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Столбец создан успешно",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ColumnResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка создания столбца",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )

            }
    )
    @PostMapping("/{board_id}")
    ResponseEntity<ColumnResponse> createColumn(@RequestBody @Valid ColumnRequest request,
                                                @PathVariable Long board_id,
                                                @Parameter(hidden = true) Principal principal);

    @Operation(
            summary = "Метод для обновления столбца",
            parameters = {
                    @Parameter(
                            name = "columnId",
                            description = "ID столбца",
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "Long")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Столбец обновлен успешно",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ColumnResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Столбец с таким id не найден",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка обновления столбца",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )

            }
    )
    @PatchMapping("/{column_id}")
    ResponseEntity<ColumnResponse> updateColumn(@PathVariable Long column_id,
                                                @RequestBody UpdateColumnRequest updateRequest,
                                                @Parameter(hidden = true) Principal principal);

    @Operation(
            summary = "Метод для удаления столбца",
            parameters = {
                    @Parameter(
                            name = "columnId",
                            description = "ID столбца",
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "Long")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Столбец удален успешно",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Столбец с таким id не найден",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )

            }
    )
    @DeleteMapping("/{column_id}")
    ResponseEntity<?> deleteColumn(@PathVariable Long column_id,
                                   @Parameter(hidden = true) Principal principal);
}
