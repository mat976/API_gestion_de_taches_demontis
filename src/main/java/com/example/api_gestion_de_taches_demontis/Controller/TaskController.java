package com.example.api_gestion_de_taches_demontis.Controller;

import com.example.api_gestion_de_taches_demontis.Entity.Task;
import com.example.api_gestion_de_taches_demontis.Entity.User;
import com.example.api_gestion_de_taches_demontis.Repository.TaskRepository;
import com.example.api_gestion_de_taches_demontis.Repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task", description = "API pour la gestion des tâches")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @Operation(summary = "Récupérer toutes les tâches")
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/ongoing")
    @Operation(summary = "Récupérer toutes les tâches en cours")
    public List<Task> getAllOngoingTasks() {
        return taskRepository.findByCompletedOrderByDueDateAsc(false);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Récupérer les tâches d'un utilisateur")
    public ResponseEntity<List<Task>> getUserTasks(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        List<Task> tasks = taskRepository.findByUserOrderByDueDateAsc(user);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/completed")
    @Operation(summary = "Récupérer les tâches complétées d'un utilisateur")
    public ResponseEntity<List<Task>> getUserCompletedTasks(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        List<Task> tasks = taskRepository.findByUserAndCompletedOrderByDueDateAsc(user, true);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/ongoing")
    @Operation(summary = "Récupérer les tâches en cours d'un utilisateur")
    public ResponseEntity<List<Task>> getUserOngoingTasks(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        List<Task> tasks = taskRepository.findByUserAndCompletedOrderByDueDateAsc(user, false);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une tâche par son ID")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle tâche")
    public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userOptional.get();
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une tâche")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Task task = taskOptional.get();
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setDueDate(taskDetails.getDueDate());
        task.setPriority(taskDetails.getPriority());
        task.setCompleted(taskDetails.isCompleted());

        if (taskDetails.getCategory() != null) {
            task.setCategory(taskDetails.getCategory());
        }

        if (taskDetails.getProject() != null) {
            task.setProject(taskDetails.getProject());
        }

        Task updatedTask = taskRepository.save(task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @PutMapping("/{id}/complete")
    @Operation(summary = "Marquer une tâche comme terminée")
    public ResponseEntity<Task> completeTask(@PathVariable Long id, @RequestParam Long userId) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Task task = taskOptional.get();
        if (task.getUser() == null || !task.getUser().getId().equals(userId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        task.setCompleted(true);
        Task updatedTask = taskRepository.save(task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une tâche")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        taskRepository.delete(taskOptional.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}