package ru.vibelab.taskmanager.security;

import jakarta.persistence.*;
import lombok.*;
import ru.vibelab.taskmanager.models.User;

import java.time.ZonedDateTime;

@Entity
@Table(name = "verify_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_value")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "expiration_time")
    private ZonedDateTime expirationDate;

    public boolean isExpired() {
        return ZonedDateTime.now().isAfter(this.expirationDate);
    }

}
