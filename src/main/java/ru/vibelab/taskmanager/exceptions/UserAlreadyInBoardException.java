package ru.vibelab.taskmanager.exceptions;

public class UserAlreadyInBoardException extends RuntimeException {
    public UserAlreadyInBoardException(Long id) {
        super("Пользователь с айди " + id + " уже добавлен в доску");
    }
}
