package ru.vibelab.taskmanager.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "picture")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "link", nullable = false, unique = true)
    private String link;

    @Column(name = "added_date", nullable = false)
    private LocalDateTime addedDate;

}
