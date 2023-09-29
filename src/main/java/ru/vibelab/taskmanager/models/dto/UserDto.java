package ru.vibelab.taskmanager.models.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import static ru.vibelab.taskmanager.models.dto.UserDto.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        NAME_PROPERTY, SURNAME_PROPERTY, POSITION_PROPERTY, AVATARLINK_PROPERTY, EMAIL_PROPERTY
})
@JsonTypeName("UserDto")
public class UserDto {

    public static final String NAME_PROPERTY = "name";
    @NotNull
    private String name;

    public static final String SURNAME_PROPERTY = "surname";
    @NotNull
    private String surname;

    public static final String POSITION_PROPERTY = "position";
    @Nullable
    private String position;

    public static final String AVATARLINK_PROPERTY = "avatarLink";
    @Nullable
    private String avatarLink;

    @Nullable
    public static final String EMAIL_PROPERTY = "email";
    private String email;

    @JsonProperty(NAME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "Valeriy")
    public String getName() {
        return name;
    }

    @JsonProperty(SURNAME_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "Petrovich")
    public String getSurname() {
        return surname;
    }

    @JsonProperty(POSITION_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "FullStack developer")
    public String getPosition() {
        return position;
    }

    @JsonProperty(AVATARLINK_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getAvatarLink() {
        return avatarLink;
    }

    @JsonProperty(EMAIL_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "valerka.gigachad@vk.com")
    public String getEmail() {
        return email;
    }
}
