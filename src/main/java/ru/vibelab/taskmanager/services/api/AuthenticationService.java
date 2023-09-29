package ru.vibelab.taskmanager.services.api;


import ru.vibelab.taskmanager.models.requests.AuthenticationRequest;
import ru.vibelab.taskmanager.models.responses.AuthenticationResponse;
import ru.vibelab.taskmanager.models.requests.RegisterRequest;
import ru.vibelab.taskmanager.util.RegistrationStatus;

public interface AuthenticationService {

    RegistrationStatus register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse confirmRegistration(String token);
}

