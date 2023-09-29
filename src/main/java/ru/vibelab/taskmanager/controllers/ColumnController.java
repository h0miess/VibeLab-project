package ru.vibelab.taskmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.vibelab.taskmanager.controllers.api.ColumnApi;
import ru.vibelab.taskmanager.models.requests.ColumnRequest;
import ru.vibelab.taskmanager.models.responses.ColumnResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateColumnRequest;
import ru.vibelab.taskmanager.services.Impl.ColumnServiceImpl;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ColumnController implements ColumnApi {

    private final ColumnServiceImpl service;


    @Override
    public ResponseEntity<ColumnResponse> createColumn(ColumnRequest request, Long board_id, Principal principal) {
        return ResponseEntity.ok(service.createColumn(request, board_id, principal)) ;
    }

    @Override
    public ResponseEntity<ColumnResponse> updateColumn(Long column_id, UpdateColumnRequest updateRequest, Principal principal) {
        return ResponseEntity.ok(service.updateColumn(column_id, updateRequest, principal));
    }

    @Override
    public ResponseEntity<?> deleteColumn(Long column_id, Principal principal) {
        service.deleteColumn(column_id, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
