package fr.imt.raimed2.security.model;

import fr.imt.raimed2.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "refresh_token")
@Table(name = "raimed_refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String token;

    @OneToOne()
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

}
