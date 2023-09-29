package ru.vibelab.taskmanager.services.api;

import lombok.NonNull;
import ru.vibelab.taskmanager.models.NotificationTypes;
import ru.vibelab.taskmanager.models.User;
import ru.vibelab.taskmanager.models.responses.NotificationResponse;

import java.security.Principal;
import java.util.List;

public interface NotificationService {

    void createNotification(@NonNull NotificationTypes type,
                            @NonNull String message,
                            @NonNull User user);

    List<NotificationResponse> getAllForUser(@NonNull Principal principal);

    NotificationResponse getNotificationById(Principal principal, Long notificationId);

    NotificationResponse markAsRead(Principal principal, Long notificationId);

    void deleteNotification(Principal principal, Long notificationId);
}
