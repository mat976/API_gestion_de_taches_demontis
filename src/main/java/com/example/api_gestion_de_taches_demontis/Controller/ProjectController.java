package com.example.api_gestion_de_taches_demontis.Controller;

import com.example.api_gestion_de_taches_demontis.DTO.ProjectDTO;
import com.example.api_gestion_de_taches_demontis.Entity.Project;
import com.example.api_gestion_de_taches_demontis.Entity.User;
import com.example.api_gestion_de_taches_demontis.Repository.ProjectRepository;
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
@RequestMapping("/api/projects")
@Tag(name = "Project", description = "API pour la gestion des projets")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @Operation(summary = "Récupérer tous les projets")
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return ProjectDTO.fromProjectList(projects);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un projet par son ID")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        return projectOpt.map(project -> ResponseEntity.ok(new ProjectDTO(project)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Récupérer les projets d'un utilisateur")
    public ResponseEntity<List<ProjectDTO>> getUserProjects(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(u -> {
            List<Project> projects = projectRepository.findByOwner(u);
            return ResponseEntity.ok(ProjectDTO.fromProjectList(projects));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau projet")
    public ResponseEntity<?> createProject(@RequestBody Project project, @RequestParam Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    if (projectRepository.existsByNameAndOwner(project.getName(), user)) {
                        return ResponseEntity.badRequest().body("Un projet avec ce nom existe déjà pour cet utilisateur");
                    }

                    project.setOwner(user);
                    Project savedProject = projectRepository.save(project);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new ProjectDTO(savedProject));
                })
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un projet")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setName(projectDetails.getName());
                    project.setDescription(projectDetails.getDescription());
                    project.setStartDate(projectDetails.getStartDate());
                    project.setEndDate(projectDetails.getEndDate());

                    Project updatedProject = projectRepository.save(project);
                    return ResponseEntity.ok(new ProjectDTO(updatedProject));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un projet")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        return projectRepository.findById(id)
                .map(project -> {
                    projectRepository.delete(project);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}