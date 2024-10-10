package fr.imt.raimed2.security;

import fr.imt.raimed2.user.model.Role;
import fr.imt.raimed2.user.model.User;
import fr.imt.raimed2.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class UserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        // Add a user with the student role
        User student = User.builder()
                .username("student")
                .firstname("Steffan")
                .lastname("Santiago")
                .email("student@raimed.fr")
                .password(passwordEncoder.encode("student"))
                .role(Role.STUDENT)
                .build();

        // Add a user with the teacher role
        User teacher = User.builder()
                .username("teacher")
                .firstname("Grayson")
                .lastname("Valdez")
                .email("teacher@raimed.fr")
                .password(passwordEncoder.encode("teacher"))
                .role(Role.TEACHER)
                .build();

        // Add a user with the admin role
        User admin = User.builder()
                .username("admin")
                .firstname("Declan")
                .lastname("George")
                .email("admin@raimed.fr")
                .password(passwordEncoder.encode("admin"))
                .role(Role.ADMIN)
                .build();

        if (userRepository.findByUsername(student.getUsername()).isEmpty()) {
            userRepository.save(student);
        }

        if (userRepository.findByUsername(teacher.getUsername()).isEmpty()) {
            userRepository.save(teacher);
        }

        if (userRepository.findByUsername(admin.getUsername()).isEmpty()) {
            userRepository.save(admin);
        }
    }
}