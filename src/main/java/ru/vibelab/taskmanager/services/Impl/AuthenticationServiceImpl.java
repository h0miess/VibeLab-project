package ru.vibelab.taskmanager.services.Impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vibelab.taskmanager.config.JwtService;
import ru.vibelab.taskmanager.exceptions.*;
import ru.vibelab.taskmanager.models.User;
import ru.vibelab.taskmanager.models.requests.AuthenticationRequest;
import ru.vibelab.taskmanager.models.requests.RegisterRequest;
import ru.vibelab.taskmanager.models.responses.AuthenticationResponse;
import ru.vibelab.taskmanager.repositories.UserRepository;
import ru.vibelab.taskmanager.repositories.VerificationTokenRepository;
import ru.vibelab.taskmanager.security.UserDetails;
import ru.vibelab.taskmanager.security.VerificationToken;
import ru.vibelab.taskmanager.services.api.AuthenticationService;
import ru.vibelab.taskmanager.util.RegistrationStatus;
import ru.vibelab.taskmanager.util.Validator;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    @Transactional
    public RegistrationStatus register(@NotNull RegisterRequest request) {

        // проверка имени и фамилии
        if(!Validator.isNameAndSurnameValid(request.getName(), request.getSurname())) {
            throw new InvalidNameException();
        }

        // если у нас уже есть пользователь с таким email, т.е. если вернулся не пустой Optional
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        // проверка на валидность пароля
        if(!Validator.isPasswordValid(request.getPassword())) throw new InvalidPasswordException();

        var user = User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()).toCharArray())
                .build();

        userRepository.save(user);

        VerificationToken verificationToken = generateVerificationToken(user);

        tokenRepository.save(verificationToken);

        emailService.sendRegisterConfirmEmail(user.getEmail(), verificationToken.getToken());

        return RegistrationStatus.NEEDS_CONFIRMATION;
    }

    @Transactional
    public AuthenticationResponse authenticate(@NotNull AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(new UserDetails(user));

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    @Transactional
    public AuthenticationResponse confirmRegistration(String token) {

        VerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(VerificationTokenNotFoundException::new);

        if(verificationToken.isExpired()) {
            throw new ExpiredConfirmationLinkException("Время на подтверждение регистрации истекло.");
        }

        User user = verificationToken.getUser();

        if(user.isEnabled()) {
            throw new RegistrationAlreadyConfirmedException();
        }

        user.setEnabled(true);
        tokenRepository.deleteByUser(user);
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(new UserDetails(user));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private VerificationToken generateVerificationToken(User user) {
        return VerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expirationDate(ZonedDateTime.now().plusHours(1))
                .build();
    }
}
