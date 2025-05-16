package com.example.api_gestion_de_taches_demontis.DTO;

import com.example.api_gestion_de_taches_demontis.Entity.Project;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDTO extends BaseDTO {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long ownerId;
    private String ownerUsername; // Ajout du nom d'utilisateur pour l'affichage
    private List<Long> taskIds = new ArrayList<>();

    // Constructeur par défaut
    public ProjectDTO() {
    }

    // Constructeur pour la conversion depuis l'entité
    public ProjectDTO(Project project) {
        setId(project.getId());
        setCreatedAt(project.getCreatedAt());
        setUpdatedAt(project.getUpdatedAt());
        this.name = project.getName();
        this.description = project.getDescription();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        
        // Informations sur le propriétaire
        if (project.getOwner() != null) {
            this.ownerId = project.getOwner().getId();
            this.ownerUsername = project.getOwner().getUsername();
        }
        
        // Convertir les listes d'entités en listes d'IDs
        if (project.getTasks() != null) {
            this.taskIds = project.getTasks().stream()
                .map(task -> task.getId())
                .collect(Collectors.toList());
        }
    }
    
    // Méthode de conversion statique pour les listes
    public static List<ProjectDTO> fromProjectList(List<Project> projects) {
        return projects.stream()
            .map(ProjectDTO::new)
            .collect(Collectors.toList());
    }

    // Getters et Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public List<Long> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<Long> taskIds) {
        this.taskIds = taskIds;
    }
}
