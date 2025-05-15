package com.example.api_gestion_de_taches_demontis.Controller;

import com.example.api_gestion_de_taches_demontis.Entity.Comment;
import com.example.api_gestion_de_taches_demontis.Entity.Task;
import com.example.api_gestion_de_taches_demontis.Entity.User;
import com.example.api_gestion_de_taches_demontis.Repository.CommentRepository;
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
@RequestMapping("/api/comments")
@Tag(name = "Comment", description = "API pour la gestion des commentaires")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/task/{taskId}")
    @Operation(summary = "Récupérer les commentaires d'une tâche")
    public ResponseEntity<List<Comment>> getCommentsByTask(@PathVariable Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        return task.map(t -> ResponseEntity.ok(commentRepository.findByTask(t)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un commentaire par son ID")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau commentaire")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment,
                                                 @RequestParam Long taskId,
                                                 @RequestParam Long userId) {
        Optional<Task> task = taskRepository.findById(taskId);
        Optional<User> user = userRepository.findById(userId);

        if (task.isPresent() && user.isPresent()) {
            comment.setTask(task.get());
            comment.setUser(user.get());
            Comment savedComment = commentRepository.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un commentaire")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment commentDetails) {
        return commentRepository.findById(id)
                .map(comment -> {
                    comment.setContent(commentDetails.getContent());
                    Comment updatedComment = commentRepository.save(comment);
                    return ResponseEntity.ok(updatedComment);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un commentaire")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        return commentRepository.findById(id)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}