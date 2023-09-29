package ru.vibelab.taskmanager.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.vibelab.taskmanager.models.responses.HoursStatsResponse.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        ALL_ESTIMATED_TIME_PROPERTY, ALL_SPENT_TIME_PROPERTY
})
@JsonTypeName("HoursStatsResponse")
public class HoursStatsResponse {

    public static final String ALL_ESTIMATED_TIME_PROPERTY = "allEstimatedTime";
    private Long allEstimatedTime;

    public static final String ALL_SPENT_TIME_PROPERTY = "allSpentTime";
    private Long allSpentTime;

    @JsonProperty(ALL_ESTIMATED_TIME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "10")
    public Long getAllEstimatedTime() {
        return allEstimatedTime;
    }

    @JsonProperty(ALL_SPENT_TIME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "2")
    public Long getAllSpentTime() {
        return allSpentTime;
    }
}
