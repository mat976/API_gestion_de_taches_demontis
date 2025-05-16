package com.example.api_gestion_de_taches_demontis.DTO;

import com.example.api_gestion_de_taches_demontis.Entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO extends BaseDTO {
    private String username;
    private String email;
    private String password; // Utilisé uniquement pour les opérations de création/mise à jour
    private List<Long> projectIds = new ArrayList<>();
    private List<Long> taskIds = new ArrayList<>();

    // Constructeur par défaut
    public UserDTO() {
    }

    // Constructeur pour la conversion depuis l'entité
    public UserDTO(User user) {
        setId(user.getId());
        setCreatedAt(user.getCreatedAt());
        setUpdatedAt(user.getUpdatedAt());
        this.username = user.getUsername();
        this.email = user.getEmail();
        
        // Convertir les listes d'entités en listes d'IDs
        if (user.getProjects() != null) {
            this.projectIds = user.getProjects().stream()
                .map(project -> project.getId())
                .collect(Collectors.toList());
        }
        
        if (user.getTasks() != null) {
            this.taskIds = user.getTasks().stream()
                .map(task -> task.getId())
                .collect(Collectors.toList());
        }
    }
    
    // Méthode de conversion statique pour les listes
    public static List<UserDTO> fromUserList(List<User> users) {
        return users.stream()
            .map(UserDTO::new)
            .collect(Collectors.toList());
    }

    // Getters et Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<Long> projectIds) {
        this.projectIds = projectIds;
    }

    public List<Long> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<Long> taskIds) {
        this.taskIds = taskIds;
    }
}
