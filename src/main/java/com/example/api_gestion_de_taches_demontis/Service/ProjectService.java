package com.example.api_gestion_de_taches_demontis.Service;

import com.example.api_gestion_de_taches_demontis.Entity.Project;
import com.example.api_gestion_de_taches_demontis.Entity.User;
import com.example.api_gestion_de_taches_demontis.Repository.ProjectRepository;
import com.example.api_gestion_de_taches_demontis.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    public Optional<List<Project>> getUserProjects(Long userId) {
        return userRepository.findById(userId)
                .map(projectRepository::findByOwner);
    }

    public Optional<Project> createProject(Project project, Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    project.setOwner(user);
                    return projectRepository.save(project);
                });
    }

    public Optional<Project> updateProject(Long id, Project projectDetails) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setName(projectDetails.getName());
                    project.setDescription(projectDetails.getDescription());
                    project.setStartDate(projectDetails.getStartDate());
                    project.setEndDate(projectDetails.getEndDate());

                    return projectRepository.save(project);
                });
    }

    public boolean deleteProject(Long id) {
        return projectRepository.findById(id)
                .map(project -> {
                    projectRepository.delete(project);
                    return true;
                })
                .orElse(false);
    }

    public boolean existsByNameAndOwner(String name, User owner) {
        return projectRepository.existsByNameAndOwner(name, owner);
    }
}