package ru.vibelab.taskmanager.services.api;

import ru.vibelab.taskmanager.models.requests.HoursStatsRequest;
import ru.vibelab.taskmanager.models.responses.HoursStatsResponse;

public interface StatsService {

    public HoursStatsResponse getHoursStatistics(HoursStatsRequest request);
}
