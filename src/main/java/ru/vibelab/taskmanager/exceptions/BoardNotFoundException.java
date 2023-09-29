package ru.vibelab.taskmanager.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class BoardNotFoundException extends EntityNotFoundException {
    public BoardNotFoundException(Long id) {
        super("Board with id " + id + " not found");
    }
}
