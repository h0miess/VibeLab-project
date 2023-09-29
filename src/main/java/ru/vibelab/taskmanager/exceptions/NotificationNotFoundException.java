package ru.vibelab.taskmanager.exceptions;

public class NotificationNotFoundException extends RuntimeException{
    public NotificationNotFoundException(Long notificationId) {
        super("Notification with id " + notificationId + " not found.");
    }
}
