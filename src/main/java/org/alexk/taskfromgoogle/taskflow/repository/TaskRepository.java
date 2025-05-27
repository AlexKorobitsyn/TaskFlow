package org.alexk.taskfromgoogle.taskflow.repository;

import org.alexk.taskfromgoogle.taskflow.model.Task;
import org.alexk.taskfromgoogle.taskflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOwner(User user);
}
