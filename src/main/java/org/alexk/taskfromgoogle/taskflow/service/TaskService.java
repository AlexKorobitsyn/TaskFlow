package org.alexk.taskfromgoogle.taskflow.service;

import lombok.RequiredArgsConstructor;
import org.alexk.taskfromgoogle.taskflow.model.Task;
import org.alexk.taskfromgoogle.taskflow.model.User;
import org.alexk.taskfromgoogle.taskflow.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getTasksForUser(User user) {
        return taskRepository.findAllByOwner(user);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task updateTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
