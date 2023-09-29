package ru.vibelab.taskmanager.models.update_requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import static ru.vibelab.taskmanager.models.update_requests.UpdateTaskRequest.*;

@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        TITLE_PROPERTY, DESCRIPTION_PROPERTY,
        BOARD_ID_PROPERTY, COLUMN_ID_PROPERTY, PARENT_TASK_ID_PROPERTY,
        ESTIMATED_TIME_PROPERTY, SPENT_TIME_PROPERTY
})
@JsonTypeName("UpdateTaskRequest")
public class UpdateTaskRequest {

    public static final String TITLE_PROPERTY = "title";
    @NotBlank(message = "Название задачи не может быть пустым")
    private String title;

    public static final String DESCRIPTION_PROPERTY = "description";
    private String description;

    public static final String BOARD_ID_PROPERTY = "board_id";
    @NotNull(message = "при создании задачи должен быть указан id доски")
    private Long board_id;

    public static final String COLUMN_ID_PROPERTY = "column_id";
    @NotNull(message = "при создании задачи должен быть указан id столбца")
    private Long column_id;

    public static final String PARENT_TASK_ID_PROPERTY = "parentTaskId";
    private Long parentTaskId;

    public static final String ESTIMATED_TIME_PROPERTY = "estimatedTime";
    private String estimatedTime;

    public static final String SPENT_TIME_PROPERTY = "spentTime";
    private String spentTime;

    @JsonProperty(TITLE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "new task title")
    public String getTitle() {
        return title;
    }

    @JsonProperty(DESCRIPTION_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "new task description")
    public String getDescription() {
        return description;
    }

    @JsonProperty(BOARD_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "10")
    public Long getBoard_id() {
        return board_id;
    }

    @JsonProperty(COLUMN_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "3")
    public Long getColumn_id() {
        return column_id;
    }

    @JsonProperty(PARENT_TASK_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "33")
    public Long getParentTaskId() {
        return this.parentTaskId;
    }

    @JsonProperty(ESTIMATED_TIME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "3н 2д 7ч 30мин")
    public String getEstimatedTime() {
        return this.estimatedTime;
    }

    @JsonProperty(SPENT_TIME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "1н 4д 20ч 11мин")
    public String getSpentTime() {
        return this.spentTime;
    }
}
