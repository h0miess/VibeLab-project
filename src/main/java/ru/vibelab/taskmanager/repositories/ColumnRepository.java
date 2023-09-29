package ru.vibelab.taskmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vibelab.taskmanager.models.ColumnEntity;
@Repository
public interface ColumnRepository extends JpaRepository<ColumnEntity, Long> {

}
