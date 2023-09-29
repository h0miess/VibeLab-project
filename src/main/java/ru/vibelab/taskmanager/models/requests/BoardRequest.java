package ru.vibelab.taskmanager.models.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import static ru.vibelab.taskmanager.models.requests.BoardRequest.PICTURE_PROPERTY;
import static ru.vibelab.taskmanager.models.requests.BoardRequest.TITLE_PROPERTY;

//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        TITLE_PROPERTY, PICTURE_PROPERTY
})
@JsonTypeName("BoardRequest")
@Schema(requiredProperties = {"title", "picture"})
public class BoardRequest {

    public static final String TITLE_PROPERTY = "title";
    @NotBlank(message = "Название доски не может быть пустым")
    private String title;

    public static final String PICTURE_PROPERTY = "picture";
    @NotBlank(message = "Доска должна иметь картинку")
    private String pictureLink;

    @JsonProperty(TITLE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "some title")
    public String getTitle() {
        return title;
    }

    @JsonProperty(PICTURE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getPictureLink() {
        return pictureLink;
    }

}
