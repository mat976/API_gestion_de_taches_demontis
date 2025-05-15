package com.example.api_gestion_de_taches_demontis.Repository;

import com.example.api_gestion_de_taches_demontis.Entity.Task;
import com.example.api_gestion_de_taches_demontis.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserOrderByDueDateAsc(User user);
    List<Task> findByUserAndCompletedOrderByDueDateAsc(User user, boolean completed);
    List<Task> findByCompletedOrderByDueDateAsc(boolean completed);
    List<Task> findByUserAndProjectId(User user, Long projectId);
    List<Task> findByUserAndCategoryId(User user, Long categoryId);
}