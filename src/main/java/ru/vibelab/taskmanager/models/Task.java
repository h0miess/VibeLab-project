package ru.vibelab.taskmanager.models;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;


@Entity
@Table(name = "task")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @Nullable
    @Column(name = "description")
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private ColumnEntity column;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    private User executor;


    @Nullable
    @Column(name = "completed_date")
    private LocalDateTime completedDate;

    @NotNull
    @Column(name = "date")
    private ZonedDateTime createdAt;

    @Nullable
    @JoinColumn(name = "parent_task_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Task parentTask;

    @Nullable
    @OneToMany(mappedBy = "parentTask", fetch = FetchType.LAZY)
    private Set<Task> childrenTasks;

    @Nullable
    @Column(name = "estimated_time")
    private Long estimatedTime;

    @Nullable
    @Column(name = "spent_time")
    private Long spentTime;

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public boolean isComplete(){
        return (completedDate != null);
    }

}
