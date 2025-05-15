package com.example.api_gestion_de_taches_demontis.Repository;

import com.example.api_gestion_de_taches_demontis.Entity.Project;
import com.example.api_gestion_de_taches_demontis.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwner(User owner);
    boolean existsByNameAndOwner(String name, User owner);
}