package ru.vibelab.taskmanager.services.api;

import ru.vibelab.taskmanager.models.requests.ColumnRequest;
import ru.vibelab.taskmanager.models.responses.ColumnResponse;
import ru.vibelab.taskmanager.models.update_requests.UpdateColumnRequest;

import java.security.Principal;

public interface ColumnService {
    ColumnResponse createColumn(ColumnRequest request, Long board_id, Principal principal);

    ColumnResponse updateColumn(Long column_id, UpdateColumnRequest updateRequest, Principal principal);

    void deleteColumn(Long column_id, Principal principal);
}
