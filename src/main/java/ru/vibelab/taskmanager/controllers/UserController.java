package ru.vibelab.taskmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vibelab.taskmanager.controllers.api.UserApi;
import ru.vibelab.taskmanager.models.requests.PasswordRequest;
import ru.vibelab.taskmanager.models.dto.UserDto;
import ru.vibelab.taskmanager.models.responses.PasswordResponse;
import ru.vibelab.taskmanager.services.api.UserService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@CrossOrigin
public class UserController implements UserApi{

    private final UserService service;

    @Override
    public ResponseEntity<UserDto> updateProfile(
            Principal principal,
            UserDto request
    ){
        return ResponseEntity.ok(service.updateProfile(principal, request));
    }

    @Override
    public ResponseEntity<UserDto> deleteProfile(
            Principal principal
    ){
        return ResponseEntity.ok(service.deleteProfile(principal));
    }

    @Override
    public ResponseEntity<PasswordResponse> updatePassword(
            Principal principal,
            PasswordRequest request
    ){
        return ResponseEntity.ok(service.updatePassword(principal,request));
    }
}
