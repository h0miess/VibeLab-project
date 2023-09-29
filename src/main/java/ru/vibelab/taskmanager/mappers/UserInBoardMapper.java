package ru.vibelab.taskmanager.mappers;

import lombok.experimental.UtilityClass;
import ru.vibelab.taskmanager.models.BoardRoles;
import ru.vibelab.taskmanager.models.User;
import ru.vibelab.taskmanager.models.responses.UserInBoardResponse;

@UtilityClass
public class UserInBoardMapper {

    public static UserInBoardResponse toResponse(User user, BoardRoles role) {
        return UserInBoardResponse.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(role.getRoleName())
                .build();
    }
}
