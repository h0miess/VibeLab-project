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

import static ru.vibelab.taskmanager.models.responses.BoardResponse.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        ID_PROPERTY, TITLE_PROPERTY, PICTURE_PROPERTY, LAST_UPDATE_DATE_PROPERTY, USERS_PROPERTY,
        COLUMNS_PROPERTY
})
@JsonTypeName("BoardResponse")
public class BoardResponse {

    public static final String ID_PROPERTY = "id";
    @NotNull
    private Long id;

    public static final String TITLE_PROPERTY = "title";
    @NotNull
    private String title;

    public static final String PICTURE_PROPERTY = "picture";
    @NotNull
    private String pictureLink;

    public static final String USERS_PROPERTY = "users";
    @NotNull
    private List<UserInBoardResponse> users;

    public static final String COLUMNS_PROPERTY = "columns";
    @Nullable
    private List<ColumnResponse> columns;

    public static final String LAST_UPDATE_DATE_PROPERTY = "last_update_date";
    @NotNull
    private String lastUpdateDate;

    @JsonProperty(ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "13")
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
    @Schema(example = "board title")
    public String getTitle() {
        return title;
    }

    @JsonProperty(TITLE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty(PICTURE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getPictureLink() {
        return pictureLink;
    }

    @JsonProperty(PICTURE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setPictureLink(String pictureLink) {
        this.pictureLink = pictureLink;
    }

    @JsonProperty(USERS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public List<UserInBoardResponse> getUsers() {
        return users;
    }

    @JsonProperty(USERS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setUsers(List<UserInBoardResponse> users) {
        this.users = users;
    }

    @JsonProperty(COLUMNS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public List<ColumnResponse> getColumns() {
        return columns;
    }

    @JsonProperty(COLUMNS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setColumns(List<ColumnResponse> columns) {
        this.columns = columns;
    }

    @JsonProperty(LAST_UPDATE_DATE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "15.08.2023 13:58")
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    @JsonProperty(LAST_UPDATE_DATE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
}
