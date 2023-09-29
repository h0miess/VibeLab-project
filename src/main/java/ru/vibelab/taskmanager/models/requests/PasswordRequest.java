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

import static ru.vibelab.taskmanager.models.requests.PasswordRequest.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        OLDPASSWORD_PROPERTY, NEWPASSWORD_PROPERTY
})
@JsonTypeName("PasswordRequest")
@Schema(requiredProperties = {"OldPassword", "NewPassword"})
public class PasswordRequest {
    @NotNull
    public static final String OLDPASSWORD_PROPERTY = "OldPassword";
    private String oldPassword;

    @NotNull
    public static final String NEWPASSWORD_PROPERTY = "NewPassword";
    private String newPassword;

    @JsonProperty(OLDPASSWORD_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getOldPassword() {
        return oldPassword;
    }

    @JsonProperty(NEWPASSWORD_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getNewPassword() {
        return newPassword;
    }
}
