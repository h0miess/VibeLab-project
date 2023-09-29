package ru.vibelab.taskmanager.models.update_requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import static ru.vibelab.taskmanager.models.update_requests.UpdateColumnRequest.NUMBER_PROPERTY;
import static ru.vibelab.taskmanager.models.update_requests.UpdateColumnRequest.TITLE_PROPERTY;


@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        TITLE_PROPERTY, NUMBER_PROPERTY
})
@JsonTypeName("UpdateColumnRequest")
public class UpdateColumnRequest {

    public static final String TITLE_PROPERTY = "title";
    @NotBlank(message = "Название столбца не может быть пустым")
    private String title;

    public static final String NUMBER_PROPERTY = "number";
    private Integer number;


    @JsonProperty(TITLE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "testing")
    public String getTitle() {
        return title;
    }

    @JsonProperty(NUMBER_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "2", description = "порядковый номер столбца")
    public Integer getNumber() {
        return number;
    }

}
