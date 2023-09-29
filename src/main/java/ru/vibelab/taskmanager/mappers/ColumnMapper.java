package ru.vibelab.taskmanager.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vibelab.taskmanager.models.ColumnEntity;
import ru.vibelab.taskmanager.models.Task;
import ru.vibelab.taskmanager.models.requests.ColumnRequest;
import ru.vibelab.taskmanager.models.responses.ColumnResponse;
import ru.vibelab.taskmanager.models.responses.TaskResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateColumnRequest;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ColumnMapper {

    private final TaskMapper taskMapper;

    public ColumnEntity requestToModel(ColumnRequest request) {
        return ColumnEntity.builder()
                .title(request.getTitle().strip())
                .build();
    }

    public ColumnResponse modelToResponse(ColumnEntity column) {

        List<Task> tasks = column.getTasks();
        List<TaskResponse> taskResponses = new ArrayList<>();

        if(tasks != null) {
            for (Task task : tasks) {
                taskResponses.add(taskMapper.modelToResponse(task));
            }
        }

        return ColumnResponse.builder()
                .id(column.getId())
                .title(column.getTitle())
                .number(column.getNumber())
                .boardId(column.getBoard().getId())
                .tasks(taskResponses)
                .build();
    }

    public void updateRequestToModel(ColumnEntity column, UpdateColumnRequest updateRequest) {
        if(updateRequest.getTitle() != null) {
            column.setTitle(updateRequest.getTitle().strip());
        }

    }
}
