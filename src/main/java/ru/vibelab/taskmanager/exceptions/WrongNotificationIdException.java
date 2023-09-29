package ru.vibelab.taskmanager.exceptions;

public class WrongNotificationIdException extends RuntimeException {
    public WrongNotificationIdException(String s) {
        super(s);
    }
}
