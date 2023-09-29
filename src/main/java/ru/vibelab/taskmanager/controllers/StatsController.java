package ru.vibelab.taskmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.vibelab.taskmanager.controllers.api.StatsApi;
import ru.vibelab.taskmanager.models.requests.HoursStatsRequest;
import ru.vibelab.taskmanager.models.requests.TaskAmountRequest;
import ru.vibelab.taskmanager.models.responses.HoursStatsResponse;
import ru.vibelab.taskmanager.models.responses.TaskAmountResponse;
import ru.vibelab.taskmanager.services.Impl.EfficiencyServiceImpl;
import ru.vibelab.taskmanager.services.Impl.StatsServiceImpl;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class StatsController implements StatsApi {

    private final StatsServiceImpl statsService;
    private final EfficiencyServiceImpl efficiencyService;

    @Override
    public ResponseEntity<HoursStatsResponse> getHoursStatistics(HoursStatsRequest request) {
        return ResponseEntity.ok(statsService.getHoursStatistics(request));
    }

    @Override
    public ResponseEntity<TaskAmountResponse> getUsersTaskAmount(Principal principal, TaskAmountRequest request){
        return ResponseEntity.ok(efficiencyService.getUsersTaskAmount(principal, request));
    }

    @Override
    public ResponseEntity<TaskAmountResponse> getUserTaskAmount(Principal principal, TaskAmountRequest request){
        return ResponseEntity.ok(efficiencyService.getUserTaskAmount(principal, request));
    }
}
