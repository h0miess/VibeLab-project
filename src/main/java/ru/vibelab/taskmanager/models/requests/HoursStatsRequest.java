package ru.vibelab.taskmanager.models.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;

import static ru.vibelab.taskmanager.models.requests.HoursStatsRequest.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        BOARD_ID_PROPERTY, USER_EMAILS_PROPERTY, PERIOD_PROPERTY
})
@Schema(requiredProperties = {
        BOARD_ID_PROPERTY
})
@JsonTypeName("HoursStatsRequest")
public class HoursStatsRequest {

    public static final String BOARD_ID_PROPERTY = "boardId";
    @NotNull(message = "должна быть указана доска")
    private Long boardId;

    public static final String USER_EMAILS_PROPERTY = "userEmail";
    @Nullable
    private List<String> userEmails;

    public static final String PERIOD_PROPERTY = "period";
    @Nullable
    private String period;

    @JsonProperty(BOARD_ID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "14")
    public Long getBoardId() {
        return boardId;
    }

    @JsonProperty(USER_EMAILS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @ArraySchema(schema = @Schema(example = "test@gmail.com"))
    public List<String> getUserEmails() {
        return userEmails;
    }

    @JsonProperty(PERIOD_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "15.07.2023 - 17.08.2023")
    public String getPeriod() {
        return period;
    }
}
