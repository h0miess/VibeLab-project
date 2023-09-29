package ru.vibelab.taskmanager.exceptions;

public class InvalidTitleException extends RuntimeException {
    public InvalidTitleException(String s) {
        super(s);
    }
}
