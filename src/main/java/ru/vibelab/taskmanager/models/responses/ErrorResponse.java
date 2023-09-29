package ru.vibelab.taskmanager.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static ru.vibelab.taskmanager.models.responses.ErrorResponse.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        ERROR_TYPE_PROPERTY, ERROR_MESSAGE_PROPERTY, ERRORS_PROPERTY
})
@JsonTypeName("ErrorResponse")
public class ErrorResponse {

    public static final String ERROR_TYPE_PROPERTY = "errorType";
    private String errorType;

    public static final String ERROR_MESSAGE_PROPERTY = "errorMessage";
    private String errorMessage;

    public static final String ERRORS_PROPERTY = "errors";
    private List<String> errors;

    @JsonProperty(ERROR_TYPE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "NotValidArgument")
    public String getErrorType() {
        return errorType;
    }

    @JsonProperty(ERROR_TYPE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    @JsonProperty(ERROR_MESSAGE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "Аргумент(ы) запроса невалидные")
    public String getErrorMessage() {
        return errorMessage;
    }

    @JsonProperty(ERROR_MESSAGE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @JsonProperty(ERRORS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @JsonProperty(ERRORS_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @ArraySchema(schema = @Schema(example = "Field: title; Error: Название доски не может быть пустым"))
    public List<String> getErrors() {
        return this.errors;
    }
}
