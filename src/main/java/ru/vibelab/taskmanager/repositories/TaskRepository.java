package ru.vibelab.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vibelab.taskmanager.models.Task;
import ru.vibelab.taskmanager.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByExecutor(User user);
}
