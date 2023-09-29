package ru.vibelab.taskmanager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.vibelab.taskmanager.controllers.api.AuthApi;
import ru.vibelab.taskmanager.models.requests.AuthenticationRequest;
import ru.vibelab.taskmanager.models.requests.RegisterRequest;
import ru.vibelab.taskmanager.models.responses.AuthenticationResponse;
import ru.vibelab.taskmanager.services.Impl.AuthenticationServiceImpl;
import ru.vibelab.taskmanager.util.RegistrationStatus;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class AuthController implements AuthApi {

    private final AuthenticationServiceImpl service;

    public ResponseEntity<AuthenticationResponse> login (AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));

    }

    public ResponseEntity<String> register(RegisterRequest request) {
        RegistrationStatus status = service.register(request);

        return switch (status) {
            case NEEDS_CONFIRMATION ->
                    ResponseEntity.ok("На вашу почту отправлено письмо-подтверждения. " +
                            "Пожалуйста, перейдите по ссылке внутри письма");
        };

    }

    @Override
    public ResponseEntity<AuthenticationResponse> confirm(String token) {
        return ResponseEntity.ok(service.confirmRegistration(token));
    }
}
