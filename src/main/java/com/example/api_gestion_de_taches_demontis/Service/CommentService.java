package com.example.api_gestion_de_taches_demontis.Service;

import com.example.api_gestion_de_taches_demontis.Entity.Comment;
import com.example.api_gestion_de_taches_demontis.Entity.Task;
import com.example.api_gestion_de_taches_demontis.Entity.User;
import com.example.api_gestion_de_taches_demontis.Repository.CommentRepository;
import com.example.api_gestion_de_taches_demontis.Repository.TaskRepository;
import com.example.api_gestion_de_taches_demontis.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<List<Comment>> getCommentsByTask(Long taskId) {
        return taskRepository.findById(taskId)
                .map(commentRepository::findByTask);
    }

    public Optional<List<Comment>> getCommentsByUser(Long userId) {
        return userRepository.findById(userId)
                .map(commentRepository::findByUser);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Optional<Comment> createComment(Comment comment, Long taskId, Long userId) {
        Optional<Task> task = taskRepository.findById(taskId);
        Optional<User> user = userRepository.findById(userId);

        if (task.isPresent() && user.isPresent()) {
            comment.setTask(task.get());
            comment.setUser(user.get());
            return Optional.of(commentRepository.save(comment));
        }

        return Optional.empty();
    }

    public Optional<Comment> updateComment(Long id, Comment commentDetails) {
        return commentRepository.findById(id)
                .map(comment -> {
                    comment.setContent(commentDetails.getContent());
                    return commentRepository.save(comment);
                });
    }

    public boolean deleteComment(Long id) {
        return commentRepository.findById(id)
                .map(comment -> {
                    commentRepository.delete(comment);
                    return true;
                })
                .orElse(false);
    }
}