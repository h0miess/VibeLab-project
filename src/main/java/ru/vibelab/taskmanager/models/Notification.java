package ru.vibelab.taskmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "notification_table")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date")
    private ZonedDateTime date;

    @NotNull
    @Column(name = "is_read")
    private boolean isRead;

    @NotNull
    @Column(name = "message")
    private String message;

    @NotNull
    @Column(name = "type")
    private NotificationTypes type;

    @NotNull
    @Column(name = "user_id")
    private Long userId;
}
