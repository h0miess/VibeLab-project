package ru.vibelab.taskmanager.exceptions;

public class ExpiredConfirmationLinkException extends RuntimeException{

    public ExpiredConfirmationLinkException(String message) {
        super(message);
    }
}
