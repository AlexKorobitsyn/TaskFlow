package org.alexk.taskfromgoogle.taskflow.controller;

import lombok.RequiredArgsConstructor;
import org.alexk.taskfromgoogle.taskflow.model.Task;
import org.alexk.taskfromgoogle.taskflow.model.User;
import org.alexk.taskfromgoogle.taskflow.repository.UserRepository;
import org.alexk.taskfromgoogle.taskflow.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<Task>> getTasks(Authentication auth) {
        User user = getCurrentUser(auth);
        return ResponseEntity.ok(taskService.getTasksForUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id, Authentication auth) {
        User user = getCurrentUser(auth);
        return taskService.getTaskById(id)
                .filter(task -> task.getOwner().getId().equals(user.getId()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(403).build()); // доступ запрещён
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task, Authentication auth) {
        User user = getCurrentUser(auth);
        task.setOwner(user);
        return ResponseEntity.ok(taskService.createTask(task));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                           @RequestBody Task updatedTask,
                                           Authentication auth) {
        User user = getCurrentUser(auth);
        return taskService.getTaskById(id)
                .filter(task -> task.getOwner().getId().equals(user.getId()))
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setDeadline(updatedTask.getDeadline());
                    task.setPriority(updatedTask.getPriority());
                    task.setStatus(updatedTask.getStatus());
                    return ResponseEntity.ok(taskService.updateTask(task));
                })
                .orElse(ResponseEntity.status(403).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id, Authentication auth) {
        User user = getCurrentUser(auth);
        return taskService.getTaskById(id)
                .filter(task -> task.getOwner().getId().equals(user.getId()))
                .map(task -> {
                    taskService.deleteTask(task.getId());
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.status(403).build());
    }

    private User getCurrentUser(Authentication auth) {
        return userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}