package ru.vibelab.taskmanager.models.responses;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static ru.vibelab.taskmanager.models.responses.ColumnResponse.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        ID_PROPERTY, TITLE_PROPERTY, NUMBER_PROPERTY, BOARD_ID_PROPERTY, TASKS_PROPERTY

})
@JsonTypeName("ColumnResponse")
public class ColumnResponse {

    @NotNull
    public static final String ID_PROPERTY = "id";
    private Long id;

    @NotNull
    public static final String TITLE_PROPERTY = "title";
    private String title;

    @NotNull
    public static final String NUMBER_PROPERTY = "number";
    private int number;

    @Nullable
    public static final String TASKS_PROPERTY = "tasks";
    private List<TaskResponse> tasks;

    @NotNull
    public static final String BOARD_ID_PROPERTY = "board_id";
    private Long boardId;

    @JsonProperty(ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "6")
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
    @Schema(example = "in progress")
    public String getTitle() {
        return title;
    }

    @JsonProperty(TITLE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty(NUMBER_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "1", description = "порядковый номер столбца")
    public int getNumber() {
        return number;
    }

    @JsonProperty(NUMBER_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setNumber(int number) {
        this.number = number;
    }

    @JsonProperty(TASKS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public List<TaskResponse> getTasks() {
        return tasks;
    }

    @JsonProperty(TASKS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setTasks(List<TaskResponse> tasks) {
        this.tasks = tasks;
    }

    @JsonProperty(BOARD_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "13")
    public Long getBoardId() {
        return boardId;
    }

    @JsonProperty(BOARD_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }
}
