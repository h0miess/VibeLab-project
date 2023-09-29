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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vibelab.taskmanager.models.responses.ErrorResponse;
import ru.vibelab.taskmanager.models.responses.NotificationResponse;

import java.security.Principal;
import java.util.List;

@Tag(
        name = "Работа с уведомлениями",
        description = "NotificationController"
)
@ApiResponse(
        responseCode = "401",
        description = "Пользователь не аутентифицирован",
        content = @Content
)
@ApiResponse(
        responseCode = "500",
        description = "Произошла ошибка на сервере",
        content = @Content
)
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/notifications")
public interface NotificationApi {

    @Operation(
            summary = "Метод для получения списка уведомлений текущего пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Уведомления получены успешно",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            array = @ArraySchema (
                                                    schema = @Schema(implementation = NotificationResponse.class)
                                            )
                                    )
                            }
                    )
            }
    )
    @GetMapping
    ResponseEntity<List<NotificationResponse>> getAllNotifications(Principal principal);

    @Operation(
            summary = "Метод для получения одного уведомления по id (относящееся к текущему пользователю)",
            parameters = {
                    @Parameter(
                            name = "notificationId",
                            description = "ID уведомления",
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "Long")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Уведомление получено успешно",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NotificationResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Уведомление не найдено",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Указанное уведомление не относится к текущему пользователю",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NotificationResponse.class)
                                    )
                            }
                    ),
            }
    )
    @GetMapping("/{notification_id}")
    ResponseEntity<NotificationResponse> getNotificationById(Principal principal, @PathVariable Long notification_id);

    @Operation(
            summary = "Метод, чтобы пометить уведомление как прочитанное (относящееся к текущему пользователю)",
            parameters = {
                    @Parameter(
                            name = "notificationId",
                            description = "ID уведомления",
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "Long")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Уведомление отмечено прочитанным успешно",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NotificationResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Уведомление не найдено",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Указанное уведомление не относится к текущему пользователю",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NotificationResponse.class)
                                    )
                            }
                    ),
            }
    )
    @PatchMapping("/{notification_id}")
    ResponseEntity<NotificationResponse> markAsRead(Principal principal, @PathVariable Long notification_id);

    @Operation(
            summary = "Метод для удаления уведомления (относящееся к текущему пользователю)",
            parameters = {
                    @Parameter(
                            name = "notificationId",
                            description = "ID уведомления",
                            in = ParameterIn.PATH,
                            schema = @Schema(type = "Long")
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Уведомление удалено успешно",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NotificationResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Уведомление не найдено",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = ErrorResponse.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Указанное уведомление не относится к текущему пользователю",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = NotificationResponse.class)
                                    )
                            }
                    ),
            }
    )
    @DeleteMapping("/{notification_id}")
    ResponseEntity<NotificationResponse> deleteNotification(Principal principal, @PathVariable Long notification_id);
}
