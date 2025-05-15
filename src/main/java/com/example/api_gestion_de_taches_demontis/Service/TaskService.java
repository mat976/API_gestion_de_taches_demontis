package com.example.api_gestion_de_taches_demontis.Service;

import com.example.api_gestion_de_taches_demontis.Entity.Task;
import com.example.api_gestion_de_taches_demontis.Entity.User;
import com.example.api_gestion_de_taches_demontis.Repository.TaskRepository;
import com.example.api_gestion_de_taches_demontis.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getAllOngoingTasks() {
        return taskRepository.findByCompletedOrderByDueDateAsc(false);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Optional<List<Task>> getUserTasks(Long userId) {
        return userRepository.findById(userId)
                .map(taskRepository::findByUserOrderByDueDateAsc);
    }

    public Optional<List<Task>> getUserCompletedTasks(Long userId) {
        return userRepository.findById(userId)
                .map(user -> taskRepository.findByUserAndCompletedOrderByDueDateAsc(user, true));
    }

    public Optional<List<Task>> getUserOngoingTasks(Long userId) {
        return userRepository.findById(userId)
                .map(user -> taskRepository.findByUserAndCompletedOrderByDueDateAsc(user, false));
    }

    public Optional<Task> createTask(Task task, Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    task.setUser(user);
                    return taskRepository.save(task);
                });
    }

    public Optional<Task> updateTask(Long id, Task taskDetails) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(taskDetails.getTitle());
                    task.setDescription(taskDetails.getDescription());
                    task.setDueDate(taskDetails.getDueDate());
                    task.setPriority(taskDetails.getPriority());
                    task.setCompleted(taskDetails.isCompleted());

                    return taskRepository.save(task);
                });
    }

    public Optional<Task> completeTask(Long id, Long userId) {
        return taskRepository.findById(id)
                .flatMap(task -> {
                    if (!task.getUser().getId().equals(userId)) {
                        return Optional.empty();
                    }
                    task.setCompleted(true);
                    return Optional.of(taskRepository.save(task));
                });
    }

    public boolean deleteTask(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    return true;
                })
                .orElse(false);
    }
}