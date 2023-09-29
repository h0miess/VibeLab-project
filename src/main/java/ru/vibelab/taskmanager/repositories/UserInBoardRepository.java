package ru.vibelab.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vibelab.taskmanager.models.UserInBoard;

import java.util.List;
import java.util.Optional;

public interface UserInBoardRepository extends JpaRepository<UserInBoard, Long> {
    Optional<UserInBoard> findByUserIdAndBoardId(Long userId, Long boardId);

    List<UserInBoard> findAllByBoardId(Long boardId);

    List<UserInBoard> findAllByUserId(Long userId);

    void deleteByUserIdAndBoardId(Long userId, Long BoardId);
}
