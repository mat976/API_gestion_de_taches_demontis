package com.example.api_gestion_de_taches_demontis.DTO;

import com.example.api_gestion_de_taches_demontis.Entity.Comment;
import java.util.List;
import java.util.stream.Collectors;

public class CommentDTO extends BaseDTO {
    private String content;
    private Long taskId;
    private String taskTitle;  // Titre de la tâche pour l'affichage
    private Long userId;
    private String username;  // Nom de l'utilisateur pour l'affichage

    // Constructeur par défaut
    public CommentDTO() {
    }

    // Constructeur pour la conversion depuis l'entité
    public CommentDTO(Comment comment) {
        setId(comment.getId());
        setCreatedAt(comment.getCreatedAt());
        setUpdatedAt(comment.getUpdatedAt());
        this.content = comment.getContent();
        
        // Informations sur la tâche
        if (comment.getTask() != null) {
            this.taskId = comment.getTask().getId();
            this.taskTitle = comment.getTask().getTitle();
        }
        
        // Informations sur l'utilisateur
        if (comment.getTask() != null && comment.getTask().getUser() != null) {
            this.userId = comment.getTask().getUser().getId();
            this.username = comment.getTask().getUser().getUsername();
        }
    }
    
    // Méthode de conversion statique pour les listes
    public static List<CommentDTO> fromCommentList(List<Comment> comments) {
        return comments.stream()
            .map(CommentDTO::new)
            .collect(Collectors.toList());
    }

    // Getters et Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
