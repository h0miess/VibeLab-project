package ru.vibelab.taskmanager.services.api;

import ru.vibelab.taskmanager.models.dto.UserDto;
import ru.vibelab.taskmanager.models.requests.PasswordRequest;
import ru.vibelab.taskmanager.models.responses.PasswordResponse;

import java.security.Principal;

public interface UserService {

    public UserDto updateProfile(Principal principal, UserDto request);

    public UserDto deleteProfile(Principal principal);

    public PasswordResponse updatePassword(Principal principal, PasswordRequest request);

}
