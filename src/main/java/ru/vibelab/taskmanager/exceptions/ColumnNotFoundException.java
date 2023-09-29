package ru.vibelab.taskmanager.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class ColumnNotFoundException extends EntityNotFoundException {
    public ColumnNotFoundException(Long id) {
        super("Column with id " + id + " not found");
    }
}
