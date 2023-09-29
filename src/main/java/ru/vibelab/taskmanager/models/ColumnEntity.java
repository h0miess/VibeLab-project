package ru.vibelab.taskmanager.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "clmn")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ColumnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "column_order")
    private int number;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Nullable
    @OneToMany(mappedBy = "column", fetch = FetchType.LAZY)
    private List<Task> tasks;

}
