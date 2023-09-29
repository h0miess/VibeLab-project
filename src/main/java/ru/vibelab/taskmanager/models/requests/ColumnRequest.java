package ru.vibelab.taskmanager.models.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import static ru.vibelab.taskmanager.models.requests.ColumnRequest.TITLE_PROPERTY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
    TITLE_PROPERTY
})
@JsonTypeName("ColumnRequest")
@Schema(requiredProperties = {"title"})
public class ColumnRequest {

    public static final String TITLE_PROPERTY = "title";
    @NotBlank(message = "Название столбца не может быть пустым")
    private String title;

    @JsonProperty(TITLE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "done")
    public String getTitle() {
        return title;
    }

}
