package ru.vibelab.taskmanager.models.responses;

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

import static ru.vibelab.taskmanager.models.responses.UserInBoardResponse.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        NAME_PROPERTY, SURNAME_PROPERTY, EMAIL_PROPERTY, ROLE_PROPERTY
})
@JsonTypeName("UserInBoardResponse")
public class UserInBoardResponse {

    public static final String NAME_PROPERTY = "name";
    @NotNull
    private String name;

    public static final String SURNAME_PROPERTY = "surname";
    @NotNull
    private String surname;

    public static final String EMAIL_PROPERTY = "email";
    @NotNull
    private String email;

    public static final String ROLE_PROPERTY = "role";
    @NotNull
    private String role;

    @JsonProperty(NAME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "Bob")
    public String getName() {
        return name;
    }

    @JsonProperty(NAME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(SURNAME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "Marley")
    public String getSurname() {
        return surname;
    }

    @JsonProperty(SURNAME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @JsonProperty(EMAIL_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "test@gmail.com")
    public String getEmail() {
        return email;
    }

    @JsonProperty(EMAIL_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty(ROLE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "admin")
    public String getRole() {
        return role;
    }

    @JsonProperty(ROLE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setRole(String role) {
        this.role = role;
    }
}
