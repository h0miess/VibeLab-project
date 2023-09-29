package ru.vibelab.taskmanager.models;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "board")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "date")
    private ZonedDateTime lastUpdateDate;

    // если не установлена картинка, ставится одна из дефолтных (фронтом)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @Nullable
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<ColumnEntity> columns;

    //    @NotNull если поставить notnull, то при создании доски будет вылетать ошибка, т.к. бд не обновится до выхода из метода
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    private List<UserInBoard> users;

//    @Transient
//    public List<User> getUsersByRole(BoardRoles role) {
//        if (this.users == null) return Collections.emptyList();
//        List<User> found = users.stream()
//                .filter(user -> user.getRole().equals(role))
//                .map(UserInBoard::getUser).toList();
//
//        return found;
//    }

    public Optional<ColumnEntity> getFinalColumn() {
        Optional<ColumnEntity> column = Optional.empty();
        if (columns != null) column = columns.stream().max(Comparator.comparing(ColumnEntity::getNumber));
        return column;
    }

}
