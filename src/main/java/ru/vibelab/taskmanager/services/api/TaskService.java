package ru.vibelab.taskmanager.services.api;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;
import ru.vibelab.taskmanager.models.requests.DelegationRequest;
import ru.vibelab.taskmanager.models.requests.TaskRequest;
import ru.vibelab.taskmanager.models.responses.TaskResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateTaskRequest;

import java.security.Principal;
import java.util.List;

public interface TaskService {

    TaskResponse createTask(TaskRequest request, Principal principal, Long columnId);

    TaskResponse updateTask(Long taskId, UpdateTaskRequest updateRequest, Principal principal);

    TaskResponse delegateTask(Long taskId, DelegationRequest delegationRequest, Principal principal);

    void deleteTask(Long taskId, Principal principal);

    TaskResponse getTask(Long taskId, Principal principal);

    TaskResponse createChildTask(Long parentTaskId, Principal principal, TaskRequest request);

    List<TaskResponse> getMyTasks(Principal principal);
}
