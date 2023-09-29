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
import ru.vibelab.taskmanager.models.requests.DelegationRequest;
import ru.vibelab.taskmanager.models.requests.TaskRequest;
import ru.vibelab.taskmanager.models.responses.ErrorResponse;
import ru.vibelab.taskmanager.models.responses.TaskResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateTaskRequest;

import java.security.Principal;
import java.util.List;

@Tag(
        name = "Работа с задачами",
        description = "TaskController"
)
@ApiResponse(
        responseCode = "401",
        description = "Пользователь не аутентифицирован",
        content = @Content
)
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/tasks")
public interface TaskApi {

    @Operation(
            summary = "Метод для создания задачи",
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
                            description = "Задача создана успешно",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = TaskResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка создания задачи",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )

            }
    )
    @PostMapping("/{column_id}")
    ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskRequest request,
                                            @Parameter(hidden = true) Principal principal,
                                            @PathVariable Long column_id);

    @Operation(
            summary = "Метод для удаления задачи",
            parameters = {
                    @Parameter(
                            name = "taskId",
                            description = "ID задачи",
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "Long")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Задача успешно удалена",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Задача с таким id не найдена",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )

            }
    )
    @DeleteMapping("/{task_id}")
    ResponseEntity<?> deleteTask(@PathVariable Long task_id, @Parameter(hidden = true) Principal principal);

    @Operation(
            summary = "Метод для обновления задачи",
            parameters = {
                    @Parameter(
                            name = "taskId",
                            description = "ID задачи",
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "Long")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Задача обновлена успешно",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = TaskResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Задача с таким id не найдена",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка обновления задачи",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )

            }
    )
    @PatchMapping("/{task_id}")
    ResponseEntity<TaskResponse> updateTask(@PathVariable Long task_id,
                                            @RequestBody UpdateTaskRequest updateRequest,
                                            @Parameter(hidden = true) Principal principal);

    @Operation(
            summary = "Метод для делегирования задачи",
            parameters = {
                    @Parameter(
                            name = "taskId",
                            description = "ID задачи",
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "Long")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Задача успешно делегирована",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = TaskResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Задача с таким id не найдена",
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
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка при делегировании задачи",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )

            }
    )
    @PatchMapping("/{task_id}/executor")
    ResponseEntity<TaskResponse> delegateTask(@PathVariable Long task_id,
                                              @RequestBody DelegationRequest delegationRequest,
                                              @Parameter(hidden = true) Principal principal);

    @Operation(
            summary = "Метод для получения задачи",
            parameters = {
                    @Parameter(
                            name = "taskId",
                            description = "ID задачи",
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "Long")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Задача успешно получена",
                            content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TaskResponse.class)
                            )
                    }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Задача с таким id не найдена",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )

            }
    )
    @GetMapping("/{task_id}")
    ResponseEntity<TaskResponse> getTask(@PathVariable Long task_id, @Parameter(hidden = true) Principal principal);

    @Operation(
            summary = "Метод для создания подзадачи. Удаление, обновление и получение происходит как для обычной задачи",
            parameters = {
                    @Parameter(
                            name = "taskId",
                            description = "ID задачи",
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "Long")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Подзадача успешно создана",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = TaskResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Родительская задача с таким id не найдена",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка создания подзадачи",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )

            }
    )
    @PostMapping("/{task_id}/childTask")
    ResponseEntity<TaskResponse> createChildTask(@PathVariable Long task_id,
                                                 @Parameter(hidden = true) Principal principal,
                                                 @RequestBody @Valid TaskRequest request);

    @Operation(
            summary = "Метод для получения \"моих задач\"",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Задачи успешно получены",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TaskResponse.class)))
                    )

            }
    )
    @GetMapping("/my")
    ResponseEntity<List<TaskResponse>> myTasks(@Parameter(hidden = true) Principal principal);
}
