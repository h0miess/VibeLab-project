package ru.vibelab.taskmanager.services.api;

import ru.vibelab.taskmanager.models.requests.BoardRequest;
import ru.vibelab.taskmanager.models.responses.BoardResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateBoardRequest;

import java.security.Principal;
import java.util.List;

public interface BoardService {

    BoardResponse createBoard(BoardRequest request, Principal principal);

    BoardResponse updateBoard(Long board_id, UpdateBoardRequest updateRequest, Principal principal);

    void deleteBoard(Long board_id, Principal principal);

    List<BoardResponse> getAllBoards(Principal principal);

    BoardResponse getBoard(Long board_id, Principal principal);

    public BoardResponse addUserInBoard(Long boardId, Long userId, Principal principal);

    public BoardResponse deleteUserFromBoard(Long boardId, Long userId, Principal principal);

}
