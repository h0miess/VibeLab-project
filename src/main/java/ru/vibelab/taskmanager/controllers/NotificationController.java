package ru.vibelab.taskmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.vibelab.taskmanager.controllers.api.NotificationApi;
import ru.vibelab.taskmanager.models.responses.NotificationResponse;
import ru.vibelab.taskmanager.services.Impl.NotificationServiceImpl;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {
    private final NotificationServiceImpl service;

    public ResponseEntity<List<NotificationResponse>> getAllNotifications(Principal principal) {
        return ResponseEntity.ok(service.getAllForUser(principal));
    }

    @Override
    public ResponseEntity<NotificationResponse> getNotificationById(Principal principal, Long notification_id) {
        return ResponseEntity.ok(service.getNotificationById(principal, notification_id));
    }

    @Override
    public ResponseEntity<NotificationResponse> markAsRead(Principal principal, Long notification_id) {
        return ResponseEntity.ok(service.markAsRead(principal, notification_id));
    }

    @Override
    public ResponseEntity<NotificationResponse> deleteNotification(Principal principal, Long notification_id) {
        service.deleteNotification(principal, notification_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
