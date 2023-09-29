package ru.vibelab.taskmanager.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vibelab.taskmanager.models.Task;
import ru.vibelab.taskmanager.models.requests.TaskRequest;
import ru.vibelab.taskmanager.models.responses.TaskResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateTaskRequest;
import ru.vibelab.taskmanager.config.PropertiesConfig;
import ru.vibelab.taskmanager.util.TimeTracker;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final PropertiesConfig propertiesConfig;

    public Task requestToModel(TaskRequest request) {

        return Task.builder()
                .title(request.getTitle().strip())
                .description(request.getDescription())
                .createdAt(ZonedDateTime.now())
                .estimatedTime(TimeTracker.stringToMinutes(request.getEstimatedTime()))
                .build();
    }

    public TaskResponse modelToResponse(Task task) {
        var taskResponse =  TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getColumn().getTitle())
                .createdByUser(UserMapper.modelToDto(task.getAuthor()))
                .executor(UserMapper.modelToDto(task.getExecutor()))
                .boardId(task.getColumn().getBoard().getId())
                .columnId(task.getColumn().getId())
                .completedDate(task.getCompletedDate())
                .createdAt(task.getCreatedAt()
                        .format(DateTimeFormatter.ofPattern(propertiesConfig.getDatePattern())))
                .build();

        if(task.getParentTask() != null) {
            taskResponse.setParentTaskId(task.getParentTask().getId());
        }

        if(task.getChildrenTasks() != null) {
            Set<Long> childrenTasksId = new HashSet<>();
            for(Task task1 : task.getChildrenTasks()) {
                childrenTasksId.add(task1.getId());
            }
            taskResponse.setChildrenTasks(childrenTasksId);
        }

        if(task.getEstimatedTime() != null) {
            taskResponse.setEstimatedTime(TimeTracker.convertToString(task.getEstimatedTime()));
        }

        if(task.getSpentTime() != null) {
            taskResponse.setSpentTime(TimeTracker.convertToString(task.getSpentTime()));
        }

        return taskResponse;
    }

    public void updateRequestToModel(Task task, UpdateTaskRequest updateRequest) {

        if(updateRequest.getTitle() != null) {
            task.setTitle(updateRequest.getTitle().strip());
        }

        if(updateRequest.getDescription() != null) {
            task.setDescription(updateRequest.getDescription());
        }

        if(updateRequest.getEstimatedTime() != null) {
            task.setEstimatedTime(TimeTracker.stringToMinutes(updateRequest.getEstimatedTime()));
        }

        if(updateRequest.getSpentTime() != null) {
            Long oldSpentTime = task.getSpentTime();

            if(oldSpentTime == null) oldSpentTime = 0L;

            Long spentTimeFromRequest = TimeTracker.stringToMinutes(updateRequest.getSpentTime());
            Long newSpentTime = oldSpentTime + spentTimeFromRequest;
            task.setSpentTime(newSpentTime);
        }

    }
}
