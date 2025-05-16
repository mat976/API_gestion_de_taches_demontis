package com.example.api_gestion_de_taches_demontis.DTO;

import com.example.api_gestion_de_taches_demontis.Entity.Task;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskDTO extends BaseDTO {
    private String title;
    private String description;
    private boolean completed;
    private LocalDate dueDate;
    private int priority;
    
    private Long userId;
    private String username; // Nom de l'utilisateur pour l'affichage
    
    private Long categoryId;
    private String categoryName; // Nom de la catégorie pour l'affichage
    
    private Long projectId;
    private String projectName; // Nom du projet pour l'affichage
    
    private List<Long> commentIds = new ArrayList<>();

    // Constructeur par défaut
    public TaskDTO() {
    }

    // Constructeur pour la conversion depuis l'entité
    public TaskDTO(Task task) {
        setId(task.getId());
        setCreatedAt(task.getCreatedAt());
        setUpdatedAt(task.getUpdatedAt());
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.completed = task.isCompleted();
        this.dueDate = task.getDueDate();
        this.priority = task.getPriority();
        
        // Informations sur l'utilisateur
        if (task.getUser() != null) {
            this.userId = task.getUser().getId();
            this.username = task.getUser().getUsername();
        }
        
        // Informations sur la catégorie
        if (task.getCategory() != null) {
            this.categoryId = task.getCategory().getId();
            this.categoryName = task.getCategory().getName();
        }
        
        // Informations sur le projet
        if (task.getProject() != null) {
            this.projectId = task.getProject().getId();
            this.projectName = task.getProject().getName();
        }
        
        // Convertir les commentaires en IDs
        if (task.getComments() != null) {
            this.commentIds = task.getComments().stream()
                .map(comment -> comment.getId())
                .collect(Collectors.toList());
        }
    }
    
    // Méthode de conversion statique pour les listes
    public static List<TaskDTO> fromTaskList(List<Task> tasks) {
        return tasks.stream()
            .map(TaskDTO::new)
            .collect(Collectors.toList());
    }

    // Getters et Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Long> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(List<Long> commentIds) {
        this.commentIds = commentIds;
    }
}
