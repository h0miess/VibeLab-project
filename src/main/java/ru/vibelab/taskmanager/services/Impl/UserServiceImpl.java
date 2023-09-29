package ru.vibelab.taskmanager.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vibelab.taskmanager.exceptions.*;
import ru.vibelab.taskmanager.models.Picture;
import ru.vibelab.taskmanager.models.User;
import ru.vibelab.taskmanager.models.dto.UserDto;
import ru.vibelab.taskmanager.models.requests.PasswordRequest;
import ru.vibelab.taskmanager.models.responses.PasswordResponse;
import ru.vibelab.taskmanager.repositories.PictureRepository;
import ru.vibelab.taskmanager.repositories.UserRepository;
import ru.vibelab.taskmanager.util.Validator;
import ru.vibelab.taskmanager.services.api.UserService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto updateProfile(Principal principal, UserDto request) {
        Long userId = userRepository.findByEmail(principal.getName()).get().getId();

        if(!Validator.isNameAndSurnameValid(request.getName(), request.getSurname())) {
            throw new InvalidNameException();
        }

        if(request.getPosition() != null)
            if(!Validator.isPositionValid(request.getPosition())){
                throw new InvalidPositionException();
            }


        if(request.getAvatarLink() != null){
            if (pictureRepository.findByLink(request.getAvatarLink()).isEmpty()){
                Picture picture = Picture.builder()
                        .link(request.getAvatarLink())
                        .addedDate(LocalDateTime.now())
                        .build();
                pictureRepository.save(picture);
            }
        }

        Optional<User> user = userRepository.findById(userId);

        user.ifPresentOrElse(
            (u) -> {
                u.setName(request.getName());
                u.setSurname(request.getSurname());
                u.setPosition(request.getPosition());
                if(request.getAvatarLink() != null) {
                    u.setAvatar(pictureRepository.findByLink(request.getAvatarLink()).get());
                }
                userRepository.save(u);},
            () -> {
                throw new UserNotFoundException(userId);});
        return request;
    }

    @Transactional
    public UserDto deleteProfile(Principal principal) {
        Long userId = userRepository.findByEmail(principal.getName()).get().getId();

        User user = userRepository.findById(userId).get();
        userRepository.deleteById(userId);
        return UserDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .position(user.getPosition())
                .avatarLink(user.getAvatar().getLink())
                .build();
    }
    @Transactional
    public PasswordResponse updatePassword(Principal principal, PasswordRequest request) {
        if(!Validator.isPasswordValid(request.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user = userRepository.findByEmail(principal.getName());
        user.ifPresentOrElse(
                (u) -> {
                    if(!passwordEncoder.encode(request.getOldPassword()).equals(u.getPassword()))
                        throw new InvalidPasswordException("Актуальный пароль не совпадает!");
                    u.setPassword(passwordEncoder.encode(request.getNewPassword()).toCharArray());
                    userRepository.save(u);},
                () -> {
                    throw new UserNotFoundException();});
        return PasswordResponse.builder()
                .password(request.getNewPassword())
                .build();
    }
}
