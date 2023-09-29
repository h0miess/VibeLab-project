package ru.vibelab.taskmanager.mappers;

import lombok.experimental.UtilityClass;
import ru.vibelab.taskmanager.models.responses.HoursStatsResponse;

@UtilityClass
public class HoursStatsMapper {

    public static HoursStatsResponse toResponse(Long estimatedTime, Long spentTime) {

        return HoursStatsResponse.builder()
                .allEstimatedTime(estimatedTime)
                .allSpentTime(spentTime)
                .build();
    }
}
