package ru.vibelab.taskmanager.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.vibelab.taskmanager.models.responses.PasswordResponse.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        PASSWORD_PROPERTY
})
@JsonTypeName("PasswordResponse")
public class PasswordResponse {

    public static final String PASSWORD_PROPERTY = "password";
    private String password;

    @JsonProperty(PASSWORD_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getPassword() {
        return password;
    }

    @JsonProperty(PASSWORD_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setPassword(String password) {
        this.password = password;
    }
}
