package ru.vibelab.taskmanager.models.requests;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.vibelab.taskmanager.models.requests.RegisterRequest.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        NAME_PROPERTY, SURNAME_PROPERTY, EMAIL_PROPERTY, PASSWORD_PROPERTY
        })
@JsonTypeName("RegisterRequest")
@Schema(requiredProperties = {"name", "surname", "email", "password"})
public class RegisterRequest {

    public static final String NAME_PROPERTY = "name";
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    public static final String SURNAME_PROPERTY = "surname";
    @NotBlank(message = "Фамилия не может быть пустой")
    private String surname;

    public static final String EMAIL_PROPERTY = "email";
    @NotBlank(message = "Почта не может быть пустой")
    private String email;

    public static final String PASSWORD_PROPERTY = "password";
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @JsonProperty(NAME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "Bob")
    public String getName() {
        return name;
    }

    @JsonProperty(SURNAME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "Johnson")
    public String getSurname() {
        return surname;
    }

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
