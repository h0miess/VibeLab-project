package ru.vibelab.taskmanager.services.api;

import ru.vibelab.taskmanager.models.requests.TaskAmountRequest;
import ru.vibelab.taskmanager.models.responses.TaskAmountResponse;

import java.security.Principal;

public interface EfficiencyService {

    public TaskAmountResponse getUsersTaskAmount(Principal principal, TaskAmountRequest request);

    public TaskAmountResponse getUserTaskAmount(Principal principal, TaskAmountRequest request);

}
