package ru.vibelab.taskmanager.models.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.vibelab.taskmanager.models.requests.DelegationRequest.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        NEWEXECUTORID_PROPERTY
})
@JsonTypeName("DelegationRequest")
@Schema(requiredProperties = {"NewExecutorId"})
public class DelegationRequest {
    @NotNull
    public static final String NEWEXECUTORID_PROPERTY = "NewExecutorId";
    private Long newExecutorId;

    @JsonProperty(NEWEXECUTORID_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public Long getNewExecutorId() {
        return newExecutorId;
    }

}
