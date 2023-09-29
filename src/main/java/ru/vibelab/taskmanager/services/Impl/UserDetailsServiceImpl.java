package ru.vibelab.taskmanager.services.Impl;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.vibelab.taskmanager.models.User;
import ru.vibelab.taskmanager.repositories.UserRepository;
import ru.vibelab.taskmanager.security.UserDetails;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> usr = userRepository.findByEmail(email);
        if(usr.isEmpty()) // Если такой пользователь не найден
            throw new UsernameNotFoundException("User not found");

        return new UserDetails(usr.get());
    }
}
