package ru.vibelab.taskmanager.models.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Duration;

import static ru.vibelab.taskmanager.models.requests.TaskAmountRequest.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        BOARDID_PROPERTY, TIME_PROPERTY
})

@JsonTypeName("TaskAmountRequest")
public class TaskAmountRequest {
    @NotNull
    public static final String BOARDID_PROPERTY = "BoardId";
    private Long boardId;

    @Nullable
    public static final String TIME_PROPERTY = "Time";
    private Duration time;

    @JsonProperty(BOARDID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Long getBoardId() {
            return boardId;
        }

    @JsonProperty(TIME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Duration getTime() {
        return time;
    }

}
