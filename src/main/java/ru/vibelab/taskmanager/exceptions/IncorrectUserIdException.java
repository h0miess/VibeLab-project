package ru.vibelab.taskmanager.exceptions;

public class IncorrectUserIdException extends RuntimeException {
    public IncorrectUserIdException(String message) {
        super(message);
    }
}
