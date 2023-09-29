package ru.vibelab.taskmanager.models.update_requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import static ru.vibelab.taskmanager.models.update_requests.UpdateBoardRequest.PICTURE_PROPERTY;
import static ru.vibelab.taskmanager.models.update_requests.UpdateBoardRequest.TITLE_PROPERTY;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        TITLE_PROPERTY, PICTURE_PROPERTY
})
@JsonTypeName("updateBoardRequest")
public class UpdateBoardRequest {

    public static final String TITLE_PROPERTY = "title";
    @NotBlank(message = "Название доски не может быть пустым")
    private String title;

    public static final String PICTURE_PROPERTY = "picture";
    private String pictureLink;

//    public static final String USERS_PROPERTY = "users"; TODO обновление списка юзеров и админов
//    private List<User> users;
//
//    public static final String ADMINS_PROPERTY = "admins";
//    private List<User> admins;


    @JsonProperty(TITLE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    @Schema(example = "new board title")
    public String getTitle() {
        return title;
    }

    @JsonProperty(PICTURE_PROPERTY)
    @JsonInclude(value = JsonInclude.Include.ALWAYS)
    public String getPictureLink() {
        return pictureLink;
    }

//    @JsonProperty(USERS_PROPERTY)
//    @JsonInclude(value = JsonInclude.Include.ALWAYS)
//    public List<User> getUsers() {
//        return users;
//    }
//
//    @JsonProperty(ADMINS_PROPERTY)
//    @JsonInclude(value = JsonInclude.Include.ALWAYS)
//    public List<User> getAdmins() {
//        return admins;
//    }

}
