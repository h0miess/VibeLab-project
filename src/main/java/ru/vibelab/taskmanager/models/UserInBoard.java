package ru.vibelab.taskmanager.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "board_to_users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private BoardRoles role;

    @Column(name = "verified")
    private Boolean isVerified;

    public UserInBoard(User user, Board board, BoardRoles role) {
        this.user = user;
        this.board = board;
        this.role = role;
    }
}
