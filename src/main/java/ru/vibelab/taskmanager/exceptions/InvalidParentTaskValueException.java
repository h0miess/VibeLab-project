package ru.vibelab.taskmanager.exceptions;

public class InvalidParentTaskValueException extends RuntimeException {
    public InvalidParentTaskValueException(String message) {
        super(message);
    }
}
