package ru.vibelab.taskmanager.models.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.vibelab.taskmanager.models.requests.TaskRequest.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        TITLE_PROPERTY, DESCRIPTION_PROPERTY, ESTIMATED_TIME_PROPERTY
})
@JsonTypeName("TaskRequest")
@Schema(requiredProperties = {"title"})
public class TaskRequest {


    public static final String TITLE_PROPERTY = "title";
    @NotBlank(message = "Название задачи не может быть пустым")
    private String title;


    public static final String DESCRIPTION_PROPERTY = "description";
    @Nullable
    private String description;


    public static final String ESTIMATED_TIME_PROPERTY = "estimatedTime";
    @Nullable
    private String estimatedTime;

    @JsonProperty(TITLE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "some title")
    public String getTitle() {
        return title;
    }

    @JsonProperty(DESCRIPTION_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "to do something")
    public String getDescription() {
        return description;
    }

    @JsonProperty(ESTIMATED_TIME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "3н 2д 7ч 30мин")
    public String getEstimatedTime() {
        return this.estimatedTime;
    }
}
