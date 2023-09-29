package ru.vibelab.taskmanager.mappers;

import lombok.experimental.UtilityClass;
import ru.vibelab.taskmanager.models.User;
import ru.vibelab.taskmanager.models.dto.UserDto;
import ru.vibelab.taskmanager.repositories.PictureRepository;

@UtilityClass
public class UserMapper {
    PictureRepository pictureRepository;
    public static UserDto modelToDto(User user) {

        return UserDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .position(user.getPosition())
                .avatarLink(user.getAvatar().getLink())
                .email(user.getEmail())
                .build();

    }

    public static User dtoToModel(UserDto userDto) {

        return User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .position(userDto.getPosition())
                .avatar(pictureRepository.findByLink(userDto.getAvatarLink()).get())
                .email(userDto.getEmail())
                .build();
    }
}
