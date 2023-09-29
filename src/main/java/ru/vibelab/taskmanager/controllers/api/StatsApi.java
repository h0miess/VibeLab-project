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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.vibelab.taskmanager.models.requests.HoursStatsRequest;
import ru.vibelab.taskmanager.models.requests.TaskAmountRequest;
import ru.vibelab.taskmanager.models.responses.ErrorResponse;
import ru.vibelab.taskmanager.models.responses.HoursStatsResponse;
import ru.vibelab.taskmanager.models.responses.TaskAmountResponse;

import java.security.Principal;

@Tag(
        name = "Статистика по доскам и пользователям",
        description = "StatsController"
)
@ApiResponse(
        responseCode = "401",
        description = "Пользователь не аутентифицирован",
        content = @Content
)
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/stats")
public interface StatsApi {

    @Operation(
            summary = "Метод для получения статистки по оцененному и затраченному времени",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Статистика успешно получена",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = HoursStatsResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Ошибка при получении статистики",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    )

            }
    )
    @PostMapping("/hours")
    ResponseEntity<HoursStatsResponse> getHoursStatistics(@RequestBody HoursStatsRequest request);

    @Operation(
            summary = "Метод для получения количества выполненых задач " +
                    "для конкретной доски за некоторый промежуток времени всеми пользователями",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Количество выполненых задач успешно получено",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = TaskAmountResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Доска не найдена",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }

                    )
            }
    )
    @GetMapping("/efficiency/users")
    ResponseEntity<TaskAmountResponse> getUsersTaskAmount(@Parameter(hidden = true) Principal principal, @RequestBody TaskAmountRequest request);

    @Operation(
            summary = "Метод для получения количества выполненых задач " +
                    "для конкретной доски за некоторый промежуток времени конкретным пользователем",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Количество выполненых задач успешно получено",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = TaskAmountResponse.class)
                                    )
                            }
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

                    )
            }
    )
    @GetMapping("/efficiency/user")
    ResponseEntity<TaskAmountResponse> getUserTaskAmount(@Parameter(hidden = true) Principal principal, @RequestBody TaskAmountRequest request);
}
