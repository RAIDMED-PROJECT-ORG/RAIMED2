package fr.imt.raimed2.user.service;

import fr.imt.raimed2.user.model.User;
import fr.imt.raimed2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> getUserById(UUID userId) {
        return this.userRepository.findById(userId);
    }

}
