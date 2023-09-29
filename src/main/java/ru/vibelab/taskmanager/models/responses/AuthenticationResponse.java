package ru.vibelab.taskmanager.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.vibelab.taskmanager.models.responses.AuthenticationResponse.TOKEN_PROPERTY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({
        TOKEN_PROPERTY
})
@JsonTypeName("AuthenticationResponse")
public class AuthenticationResponse {

    public static final String TOKEN_PROPERTY = "token";
    private String token;

    @JsonProperty(TOKEN_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTY5MTMxOTAyMiwiZXhwIjoxNjkxNDA1NDIyfQ.XPTaedO6GOPibL9YSHRBsRSlzk7exGhPNT93a5KWd0o")
    public String getToken() {
        return token;
    }

    @JsonProperty(TOKEN_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public void setToken(String token) {
        this.token = token;
    }
}
