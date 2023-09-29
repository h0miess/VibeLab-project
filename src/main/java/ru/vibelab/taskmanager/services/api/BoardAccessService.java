package ru.vibelab.taskmanager.services.api;

import ru.vibelab.taskmanager.models.Board;

import java.security.Principal;

public interface BoardAccessService {
    boolean checkAccessToBoard(Board board, Principal principal);

    boolean checkAdminAccessToBoard(Board board, Principal principal);
}
