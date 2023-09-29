package ru.vibelab.taskmanager.mappers;

import lombok.experimental.UtilityClass;
import ru.vibelab.taskmanager.models.Notification;
import ru.vibelab.taskmanager.models.responses.NotificationResponse;

import java.time.format.DateTimeFormatter;

@UtilityClass
public class NotificationMapper {

    public static NotificationResponse modelToResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .date(notification.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
                .isRead(notification.isRead())
                .message(notification.getMessage())
                .type(notification.getType())
                .userId(notification.getUserId())
                .build();
    }
}
