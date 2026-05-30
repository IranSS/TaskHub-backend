package backend.application.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.application.models.TaskModel;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
    // Metodo para encontrar tarefas pelo id do usuário
    List<TaskModel> findByUserId(UUID userId);
}
