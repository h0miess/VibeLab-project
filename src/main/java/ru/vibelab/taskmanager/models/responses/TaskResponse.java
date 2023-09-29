package ru.vibelab.taskmanager.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.vibelab.taskmanager.models.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Set;

import static ru.vibelab.taskmanager.models.responses.TaskResponse.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        ID_PROPERTY, TITLE_PROPERTY, DESCRIPTION_PROPERTY, STATUS_PROPERTY, BOARD_ID_PROPERTY,
        COLUMN_ID_PROPERTY, CREATED_AT_PROPERTY, PARENT_TASK_ID_PROPERTY, CHILDREN_TASKS_PROPERTY,
        ESTIMATED_TIME_PROPERTY, SPENT_TIME_PROPERTY, COMPLETED_DATE_PROPERTY, CREATED_BY_USER_PROPERTY,
        EXECUTOR_PROPERTY
})
@JsonTypeName("TaskResponse")
public class TaskResponse {

    public static final String ID_PROPERTY = "id";
    @NotNull
    private Long id;

    public static final String TITLE_PROPERTY = "title";
    @NotNull
    private String title;

    public static final String DESCRIPTION_PROPERTY = "description";
    @Nullable
    private String description;

    public static final String STATUS_PROPERTY = "status";
    @NotNull
    private String status;

    public static final String CREATED_BY_USER_PROPERTY = "created_by_user";
    @NotNull
    private UserDto createdByUser;

    public static final String EXECUTOR_PROPERTY = "executor";
    @NotNull
    private UserDto executor;

    public static final String BOARD_ID_PROPERTY = "boardId";
    @NotNull
    private Long boardId;

    public static final String COLUMN_ID_PROPERTY = "columnId";
    @NotNull
    private Long columnId;

    public static final String PARENT_TASK_ID_PROPERTY = "parentTaskId";
    @Nullable
    private Long parentTaskId;

    public static final String CHILDREN_TASKS_PROPERTY = "childrenTasks";
    @Nullable
    private Set<Long> childrenTasks;

    public static final String CREATED_AT_PROPERTY = "createdAt";
    @NotNull
    private String createdAt;

    public static final String ESTIMATED_TIME_PROPERTY = "estimatedTime";
    @Nullable
    private String estimatedTime;

    public static final String SPENT_TIME_PROPERTY = "spentTime";
    @Nullable
    private String spentTime;

    public static final String COMPLETED_DATE_PROPERTY = "completed_date";
    @Nullable
    private LocalDateTime completedDate;

    @JsonProperty(ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "59")
    public Long getId() {
        return id;
    }

    @JsonProperty(ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty(TITLE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "task title")
    public String getTitle() {
        return title;
    }

    @JsonProperty(TITLE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty(DESCRIPTION_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "fix bugs")
    public String getDescription() {
        return description;
    }

    @JsonProperty(DESCRIPTION_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty(STATUS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "in progress",
            description = "Соответствует названию столбца, в котом находится задача")
    public String getStatus() {
        return status;
    }

    @JsonProperty(STATUS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty(CREATED_BY_USER_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public UserDto getCreatedByUser() {
        return createdByUser;
    }

    @JsonProperty(CREATED_BY_USER_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setCreatedByUser(UserDto createdByUser) {
        this.createdByUser = createdByUser;
    }

    @JsonProperty(BOARD_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "3")
    public Long getBoardId() {
        return boardId;
    }

    @JsonProperty(BOARD_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    @JsonProperty(COLUMN_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "6")
    public Long getColumnId() {
        return columnId;
    }

    @JsonProperty(COLUMN_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    @JsonProperty(PARENT_TASK_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setParentTaskId(Long parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    @JsonProperty(PARENT_TASK_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "39",
            description = "айди родительской задачи")
    public Long getParentTaskId() {
        return this.parentTaskId;
    }

    @JsonProperty(CHILDREN_TASKS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setChildrenTasks(Set<Long> childrenTasks) {
        this.childrenTasks = childrenTasks;
    }

    @JsonProperty(CHILDREN_TASKS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @ArraySchema(schema = @Schema(example = "6", description = "список подзадач"))
    public Set<Long> getChildrenTasks() {
        return this.childrenTasks;
    }

    @JsonProperty(CREATED_AT_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty(CREATED_AT_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "23.01.2022")
    public String getCreatedAt() {
        return this.createdAt;
    }

    @JsonProperty(ESTIMATED_TIME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    @JsonProperty(ESTIMATED_TIME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "3н 2д 7ч 30мин")
    public String getEstimatedTime() {
        return this.estimatedTime;
    }

    @JsonProperty(SPENT_TIME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setSpentTime(String spentTime) {
        this.spentTime = spentTime;
    }

    @JsonProperty(SPENT_TIME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "1н 4д 20ч 11мин")
    public String getSpentTime() {
        return this.spentTime;
    }

        @JsonProperty(COMPLETED_DATE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public LocalDateTime getCompletedDate() {
        return completedDate;
    }

    @JsonProperty(COMPLETED_DATE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setCompletedDate(LocalDateTime completedDate) {
        this.completedDate = completedDate;
    }

    @JsonProperty(EXECUTOR_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public UserDto getExecutor() {
        return executor;
    }

    @JsonProperty(EXECUTOR_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setExecutor(UserDto executor) {
        this.executor = executor;
    }
}
