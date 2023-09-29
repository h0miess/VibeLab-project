package ru.vibelab.taskmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.vibelab.taskmanager.controllers.api.TaskApi;
import ru.vibelab.taskmanager.models.requests.DelegationRequest;
import ru.vibelab.taskmanager.models.requests.TaskRequest;
import ru.vibelab.taskmanager.models.responses.TaskResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateTaskRequest;
import ru.vibelab.taskmanager.services.Impl.TaskServiceImpl;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class TaskController implements TaskApi {

    private final TaskServiceImpl service;

    @Override
    public ResponseEntity<TaskResponse> createTask(TaskRequest request,
                                                   Principal principal,
                                                   Long column_id) {
        return ResponseEntity.ok(service.createTask(request, principal, column_id));
    }

    @Override
    public ResponseEntity<?> deleteTask(Long task_id, Principal principal) {
        service.deleteTask(task_id, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TaskResponse> updateTask(Long task_id, UpdateTaskRequest updateRequest, Principal principal) {
        return ResponseEntity.ok(service.updateTask(task_id, updateRequest, principal));
    }

    @Override
    public ResponseEntity<TaskResponse> delegateTask(Long task_id,
                                              DelegationRequest delegationRequest,
                                              Principal principal){
        return ResponseEntity.ok(service.delegateTask(task_id, delegationRequest, principal));
    }

    @Override
    public ResponseEntity<TaskResponse> getTask(Long task_id, Principal principal) {
        return ResponseEntity.ok(service.getTask(task_id, principal));
    }

    @Override
    public ResponseEntity<TaskResponse> createChildTask(Long task_id, Principal principal, TaskRequest request) {
        return ResponseEntity.ok(service.createChildTask(task_id, principal, request));
    }

    @Override
    public ResponseEntity<List<TaskResponse>> myTasks(Principal principal) {
        return ResponseEntity.ok(service.getMyTasks(principal));
    }
}

