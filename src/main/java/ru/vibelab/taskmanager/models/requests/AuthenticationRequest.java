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

import static ru.vibelab.taskmanager.models.requests.AuthenticationRequest.EMAIL_PROPERTY;
import static ru.vibelab.taskmanager.models.requests.AuthenticationRequest.PASSWORD_PROPERTY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        EMAIL_PROPERTY, PASSWORD_PROPERTY
})
@JsonTypeName("AuthenticationRequest")
@Schema(requiredProperties = {"email", "password"})
public class AuthenticationRequest {

    public static final String EMAIL_PROPERTY = "email";
    @NotNull
    private String email;

    public static final String PASSWORD_PROPERTY = "password";
    @NotNull
    private String password;


    @JsonProperty(EMAIL_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "test@test.com")
    public String getEmail() {
        return email;
    }

    @JsonProperty(PASSWORD_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "password1234")
    public String getPassword() {
        return password;
    }

}
