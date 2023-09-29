package ru.vibelab.taskmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import ru.vibelab.taskmanager.security.VerificationToken;

@Entity
@Table(name = "usr")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "position")
    private String position;

    @Column(name = "password", nullable = false)
    private char[] password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id")
    private Picture avatar;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private VerificationToken verificationToken;

    public String getPassword() {
        return String.valueOf(this.password);
    }

}
