package ru.vibelab.taskmanager.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class TaskNotFoundException extends EntityNotFoundException {
    public TaskNotFoundException(Long id) {
        super("Task with id " + id + " not found");
    }
}
