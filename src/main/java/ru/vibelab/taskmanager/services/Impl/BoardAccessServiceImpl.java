package ru.vibelab.taskmanager.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vibelab.taskmanager.models.Board;
import ru.vibelab.taskmanager.models.BoardRoles;
import ru.vibelab.taskmanager.models.User;
import ru.vibelab.taskmanager.models.UserInBoard;
import ru.vibelab.taskmanager.repositories.UserRepository;
import ru.vibelab.taskmanager.services.api.BoardAccessService;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class BoardAccessServiceImpl implements BoardAccessService {

    private final UserRepository userRepository;
    @Override
    public boolean checkAccessToBoard(Board board, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).get();

        return board.getUsers().stream().map(UserInBoard::getUser).toList().contains(user);
    }

    @Override
    public boolean checkAdminAccessToBoard(Board board, Principal principal) {
        if(!checkAccessToBoard(board, principal)) return false;

        User user = userRepository.findByEmail(principal.getName()).get();

        return board.getUsers().stream()
                .filter(userInBoard -> userInBoard.getRole().equals(BoardRoles.ADMIN))
                .map(UserInBoard::getUser)
                .toList()
                .contains(user);
    }
}
