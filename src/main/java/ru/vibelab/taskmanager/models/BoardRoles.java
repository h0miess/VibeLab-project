package ru.vibelab.taskmanager.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardRoles {
    USER("user"), ADMIN("admin");

    private final String roleName;

}
