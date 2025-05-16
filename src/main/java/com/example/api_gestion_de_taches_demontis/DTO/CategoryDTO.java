package com.example.api_gestion_de_taches_demontis.DTO;

import com.example.api_gestion_de_taches_demontis.Entity.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryDTO extends BaseDTO {
    private String name;
    private String color;
    private List<Long> taskIds = new ArrayList<>();

    // Constructeur par défaut
    public CategoryDTO() {
    }

    // Constructeur pour la conversion depuis l'entité
    public CategoryDTO(Category category) {
        setId(category.getId());
        setCreatedAt(category.getCreatedAt());
        setUpdatedAt(category.getUpdatedAt());
        this.name = category.getName();
        this.color = category.getColor();
        
        // Convertir les tâches en IDs
        if (category.getTasks() != null) {
            this.taskIds = category.getTasks().stream()
                .map(task -> task.getId())
                .collect(Collectors.toList());
        }
    }
    
    // Méthode de conversion statique pour les listes
    public static List<CategoryDTO> fromCategoryList(List<Category> categories) {
        return categories.stream()
            .map(CategoryDTO::new)
            .collect(Collectors.toList());
    }

    // Getters et Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Long> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<Long> taskIds) {
        this.taskIds = taskIds;
    }
}
