package ru.vibelab.taskmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.vibelab.taskmanager.controllers.api.BoardApi;
import ru.vibelab.taskmanager.models.requests.BoardRequest;
import ru.vibelab.taskmanager.models.responses.BoardResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateBoardRequest;
import ru.vibelab.taskmanager.services.Impl.BoardServiceImpl;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class BoardController implements BoardApi {

    private final BoardServiceImpl service;

    @Override
    public ResponseEntity<List<BoardResponse>> getAllBoards(Principal principal) {

        return ResponseEntity.ok(service.getAllBoards(principal));
    }

    @Override
    public ResponseEntity<BoardResponse> createBoard(BoardRequest request, Principal principal) {
        return ResponseEntity.ok(service.createBoard(request, principal));
    }

    @Override
    public ResponseEntity<BoardResponse> getBoardById(Long board_id, Principal principal) {
        return ResponseEntity.ok(service.getBoard(board_id, principal));
    }

    @Override
    public ResponseEntity<BoardResponse> updateBoard(Long board_id, UpdateBoardRequest updateRequest,
                                                     Principal principal) {
        return ResponseEntity.ok(service.updateBoard(board_id, updateRequest, principal));
    }

    @Override
    public ResponseEntity<?> deleteBoard(Long board_id, Principal principal) {
        service.deleteBoard(board_id, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<BoardResponse> addUserInBoard(Long boardId, Long userId, Principal principal){
        return ResponseEntity.ok(service.addUserInBoard(boardId, userId, principal));
    }

    public ResponseEntity<BoardResponse> deleteUserFromBoard(Long boardId, Long userId, Principal principal){
        return ResponseEntity.ok(service.deleteUserFromBoard(boardId, userId, principal));
    }


}
