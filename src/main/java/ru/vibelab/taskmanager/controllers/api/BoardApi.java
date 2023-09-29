package ru.vibelab.taskmanager.controllers.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vibelab.taskmanager.models.requests.BoardRequest;
import ru.vibelab.taskmanager.models.responses.BoardResponse;
import ru.vibelab.taskmanager.models.responses.ErrorResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateBoardRequest;

import java.security.Principal;
import java.util.List;
@Tag(
        name = "Работа с досками",
        description = "BoardController"
)
@ApiResponse(
        responseCode = "401",
        description = "Пользователь не аутентифицирован",
        content = @Content
)
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/boards")
public interface BoardApi {

    @Operation(
            summary = "Метод для получения списка досок",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список досок получен успешно",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = BoardResponse.class)
                                    )
                            )
                    )
            }
    )
    @GetMapping("")
    ResponseEntity<List<BoardResponse>> getAllBoards(@Parameter(hidden = true) Principal principal);

    @Operation(
            summary = "Метод для создания доски",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Доска создана успешно",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = BoardResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка создания доски",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )

            }
    )
    @PostMapping("")
    ResponseEntity<BoardResponse> createBoard(@Valid @RequestBody BoardRequest request,
                                              @Parameter(hidden = true) Principal principal);

    @Operation(
            summary = "Метод для получения одной доски",
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
                            description = "Доска получена успешно",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = BoardResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Доска с таким id не найдена",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )
            }
    )
    @GetMapping("/{board_id}")
    ResponseEntity<BoardResponse> getBoardById(@PathVariable Long board_id,
                                               @Parameter(hidden = true) Principal principal);

    @Operation(
            summary = "Метод для обновления доски",
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
                            description = "Доска обновлена успешно",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = BoardResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Доска с таким id не найдена",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка обновления доски",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )

            }
    )
    @PatchMapping("/{board_id}")
    ResponseEntity<BoardResponse> updateBoard(@PathVariable Long board_id,
                                              @RequestBody UpdateBoardRequest updateRequest,
                                              @Parameter(hidden = true) Principal principal);

    @Operation(
            summary = "Метод для удаления доски",
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
                            description = "Доска удалена успешно",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Доска с таким id не найдена",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )
            }
    )
    @DeleteMapping("/{board_id}")
    ResponseEntity<?> deleteBoard(@PathVariable Long board_id,
                                  @Parameter(hidden = true) Principal principal);

    @Operation(
            summary = "Метод для добавления пользователя в доску",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "ID пользователя",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "Long")
                    ),
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
                            description = "Пользователь успешно добавлен",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Доска или пользователь не найдены",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Нет доступа к добавлению пользователей в эту доску",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )
            }
    )
    @PatchMapping("/{boardId}/user")
    ResponseEntity<BoardResponse> addUserInBoard(@PathVariable Long boardId,
                                                 @RequestParam Long userId,
                                                 @Parameter(hidden = true) Principal principal);

    @Operation(
            summary = "Метод для удаления пользователя из доски",
            parameters = {
                    @Parameter(
                            name = "userId",
                            description = "ID пользователя",
                            in = ParameterIn.QUERY,
                            schema = @Schema(type = "Long")
                    ),
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
                            description = "Пользователь успешно удален",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Доска или пользователь не найдены",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Нет доступа к удалению пользователей из этой доски",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )
            }
    )
    @DeleteMapping("/{boardId}/user")
    ResponseEntity<BoardResponse> deleteUserFromBoard(@PathVariable Long boardId,
                                                      @RequestParam Long userId,
                                                      @Parameter(hidden = true) Principal principal);
}
