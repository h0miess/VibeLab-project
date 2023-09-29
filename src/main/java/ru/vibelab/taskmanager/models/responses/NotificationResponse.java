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
import ru.vibelab.taskmanager.models.NotificationTypes;

import static ru.vibelab.taskmanager.models.responses.NotificationResponse.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
    ID_PROPERTY, TYPE_PROPERTY, MESSAGE_PROPERTY, IS_READ_PROPERTY, USER_ID_PROPERTY, DATE_PROPERTY
})
@JsonTypeName("NotificationResponse")
public class NotificationResponse {

    public static final String ID_PROPERTY = "id";
    private Long id;

    public static final String DATE_PROPERTY = "date";
    private String date;

    public static final String IS_READ_PROPERTY = "isRead";
    private boolean isRead;

    public static final String MESSAGE_PROPERTY = "message";
    private String message;

    public static final String TYPE_PROPERTY = "type";
    private NotificationTypes type;

    public static final String USER_ID_PROPERTY = "userId";
    private Long userId;

    @JsonProperty(ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "6")
    public Long getId() {
        return id;
    }

    @JsonProperty(DATE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "15.08.2023 13:58")
    public String getDate() {
        return date;
    }

    @JsonProperty(IS_READ_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "true")
    public boolean isRead() {
        return isRead;
    }

    @JsonProperty(MESSAGE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "Была создана доска с названием новая доска")
    public String getMessage() {
        return message;
    }

    @JsonProperty(TYPE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "NEW_BOARD")
    public NotificationTypes getType() {
        return type;
    }

    @JsonProperty(USER_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "1")
    public Long getUserId() {
        return userId;
    }
}

