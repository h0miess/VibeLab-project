package ru.vibelab.taskmanager.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.vibelab.taskmanager.models.responses.TaskAmountResponse.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        AMOUNT_PROPERTY
})
@JsonTypeName("TaskAmountResponse")
public class TaskAmountResponse {

    public static final String AMOUNT_PROPERTY = "amount";
    private long amount;

    @JsonProperty(AMOUNT_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public long getAmount() {
        return amount;
    }

    @JsonProperty(AMOUNT_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setAmount(long amount) {
        this.amount = amount;
    }
}
