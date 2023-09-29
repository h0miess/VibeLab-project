package ru.vibelab.taskmanager.services.Impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vibelab.taskmanager.exceptions.*;
import ru.vibelab.taskmanager.mappers.TaskMapper;
import ru.vibelab.taskmanager.models.Board;
import ru.vibelab.taskmanager.models.ColumnEntity;
import ru.vibelab.taskmanager.models.Task;
import ru.vibelab.taskmanager.models.User;
import ru.vibelab.taskmanager.models.requests.DelegationRequest;
import ru.vibelab.taskmanager.models.requests.TaskRequest;
import ru.vibelab.taskmanager.models.responses.TaskResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateTaskRequest;
import ru.vibelab.taskmanager.repositories.BoardRepository;
import ru.vibelab.taskmanager.repositories.ColumnRepository;
import ru.vibelab.taskmanager.repositories.TaskRepository;
import ru.vibelab.taskmanager.repositories.UserRepository;
import ru.vibelab.taskmanager.services.api.TaskService;
import ru.vibelab.taskmanager.util.Validator;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ColumnRepository columnRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final BoardAccessServiceImpl boardAccessService;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public TaskResponse createTask(@NotNull TaskRequest request,
                                   @NotNull Principal principal,
                                   @NotNull Long columnId) {

        ColumnEntity column = columnRepository.findById(columnId).orElseThrow(() -> new ColumnNotFoundException(columnId));

        if (!boardAccessService.checkAccessToBoard(column.getBoard(), principal))
            throw new NoAccessException("Вы не имеете доступа к этой доске.");

        validateTaskTitle(request.getTitle());

        if (request.getEstimatedTime() != null && !Validator.isStringWithTimeValid(request.getEstimatedTime()))
            throw new InvalidTimeException("Некорректно указано время");

        Task task = taskMapper.requestToModel(request);

        task.setColumn(column);

        task.setAuthor(userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UserNotFoundException("User with email " + principal.getName() + " not found")));

        task.setExecutor(userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UserNotFoundException("User with email " + principal.getName() + " not found")));

        taskRepository.save(task);
        updateBoardLastUpdateTime(column.getBoard());
        return taskMapper.modelToResponse(task);
    }

    @Override
    @Transactional
    public TaskResponse updateTask(@NotNull Long taskId,
                                   @Nullable UpdateTaskRequest updateRequest,
                                   Principal principal) {

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        if (!boardAccessService.checkAccessToBoard(task.getColumn().getBoard(), principal))
            throw new NoAccessException("Вы не имеете доступа к этой доске.");

        if (updateRequest == null) {
            return taskMapper.modelToResponse(task);
        }

        if (updateRequest.getTitle() != null) {
            validateTaskTitle(updateRequest.getTitle());
        }

        if (updateRequest.getEstimatedTime() != null && !Validator.isStringWithTimeValid(updateRequest.getEstimatedTime()))
            throw new InvalidTimeException("Некорректно указано время");

        if (updateRequest.getSpentTime() != null && !Validator.isStringWithTimeValid(updateRequest.getSpentTime()))
            throw new InvalidTimeException("Некорректно указано время");

        taskMapper.updateRequestToModel(task, updateRequest);

        if (updateRequest.getColumn_id() != null) {
            task.setColumn(columnRepository
                    .findById(updateRequest.getColumn_id())
                    .orElseThrow(() -> new ColumnNotFoundException(updateRequest.getColumn_id())));
        }

        if (isCompleted(task)) {
            task.setCompletedDate(LocalDateTime.now());
        }

        if (updateRequest.getParentTaskId() != null) {

            if (updateRequest.getParentTaskId().equals(taskId)) {
                throw new InvalidParentTaskValueException("Родителем не может быть эта же задача");
            }

            Long parentTaskId = updateRequest.getParentTaskId();
            Task parentTask = taskRepository.findById(parentTaskId)
                    .orElseThrow(() -> new TaskNotFoundException(parentTaskId));

            task.setParentTask(parentTask);
        }

        taskRepository.save(task);
        updateBoardLastUpdateTime(task.getColumn().getBoard());

        return taskMapper.modelToResponse(task);
    }

    @Override
    @Transactional
    public TaskResponse delegateTask(@NotNull Long taskId,
                                     DelegationRequest delegationRequest,
                                     Principal principal) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        if (!boardAccessService.checkAccessToBoard(task.getColumn().getBoard(), principal))
            throw new NoAccessException("Вы не имеете доступа к этой доске.");

        if (!principal.getName().equals(task.getAuthor().getEmail()) && !principal.getName().equals(task.getExecutor().getEmail()))
            throw new NoAccessException("Вы не можете делегировать задачу, " +
                    "т. к. не являетесь её создателем или исполнителем");

        User user = userRepository.findById(delegationRequest.getNewExecutorId())
                .orElseThrow(() -> new UserNotFoundException(delegationRequest.getNewExecutorId()));

        task.setExecutor(user);
        taskRepository.save(task);
        updateBoardLastUpdateTime(task.getColumn().getBoard());

        return taskMapper.modelToResponse(task);
    }

    @Override
    @Transactional
    public void deleteTask(@NotNull Long taskId, Principal principal) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        if (!boardAccessService.checkAccessToBoard(task.getColumn().getBoard(), principal))
            throw new NoAccessException("Вы не имеете доступа к этой доске.");

        taskRepository.deleteById(taskId);
        updateBoardLastUpdateTime(task.getColumn().getBoard());
    }

    @Override
    public TaskResponse getTask(@NotNull Long taskId, Principal principal) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));

        if (!boardAccessService.checkAccessToBoard(task.getColumn().getBoard(), principal))
            throw new NoAccessException("Вы не имеете доступа к этой доске.");

        return taskMapper.modelToResponse(task);
    }

    @Override
    @Transactional
    public TaskResponse createChildTask(Long parentTaskId, Principal principal, TaskRequest request) {
        Task parentTask = taskRepository.findById(parentTaskId).orElseThrow(() -> new TaskNotFoundException(parentTaskId));

        if (!boardAccessService.checkAccessToBoard(parentTask.getColumn().getBoard(), principal))
            throw new NoAccessException("Вы не имеете доступа к этой доске.");

        Task childTask = taskMapper.requestToModel(request);

        ColumnEntity columnOfParentTask = columnRepository.findById(parentTask.getColumn().getId()).get();
        User authorOfParentTask = userRepository.findById(parentTask.getAuthor().getId()).get();

        childTask.setParentTask(parentTask);
        childTask.setColumn(columnOfParentTask);
        childTask.setExecutor(authorOfParentTask);
        childTask.setAuthor(authorOfParentTask);

        taskRepository.save(childTask);
        updateBoardLastUpdateTime(parentTask.getColumn().getBoard());

        return taskMapper.modelToResponse(childTask);

    }

    @Override
    public List<TaskResponse> getMyTasks(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).get();
        List<Task> myTasks = taskRepository.findAllByExecutor(user);

        List<TaskResponse> taskResponses = myTasks.stream().map(taskMapper::modelToResponse).toList();
        return taskResponses;
    }

    private boolean isCompleted(Task task) {
        if (task.getColumn().getBoard().getFinalColumn().isEmpty())
            return false;
        return task.getColumn().getBoard().getFinalColumn().get().getNumber()
                == task.getColumn().getNumber();
    }

    private void validateTaskTitle(String title) {
        if (!Validator.isTitleValid(title)) {
            throw new InvalidTitleException("Некорректное название задачи. Могут быть использованы только буквы " +
                    "российского алфавита, латиница, цифры, нижнее подчеркивание, дефис и пробел");
        }

        if (title.isBlank()) {
            throw new InvalidTitleException("Название задачи не может быть пустым");
        }
    }

    private void updateBoardLastUpdateTime(Board board) {
        board.setLastUpdateDate(ZonedDateTime.now());
        boardRepository.save(board);
    }
}
