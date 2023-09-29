package ru.vibelab.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vibelab.taskmanager.models.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

}
