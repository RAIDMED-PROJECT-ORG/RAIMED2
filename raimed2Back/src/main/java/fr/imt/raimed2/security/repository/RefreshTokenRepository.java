package fr.imt.raimed2.security.repository;

import fr.imt.raimed2.security.model.RefreshToken;
import fr.imt.raimed2.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByUser(User user);

    void deleteByUser(User user);

}
