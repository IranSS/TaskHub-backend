package backend.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import backend.application.DTO.task.TaskDTO;
import backend.application.exceptions.ResourceNotFoundException;
import backend.application.models.TaskModel;
import backend.application.models.user.UserModel;
import backend.application.repositories.TaskRepository;
import backend.application.repositories.UserRepository;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    // Cria a tarefa e associa ao usuário autenticado, garante que cada tarefa seja vinculada a um usuário
    public TaskDTO create(TaskDTO dto){
        UserModel user = getAuthenticatedUser();

        TaskModel task = new TaskModel();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setCompleted(dto.completed());
        task.setUser(user);

        return toDTO(taskRepository.save(task));
    }

    // Verifica se a tarefa existe e se pertence ao usuário logado
    public TaskDTO findByID(UUID id){
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));
                
        validateOwnership(task);
        return toDTO(task);
        
    }

    // Lista todas as tarefas do usuário autenticado
    public List<TaskDTO> findByAuthenticatedUser(){
        UserModel user = getAuthenticatedUser();

        return taskRepository.findByUserId(user.getId())
            .stream()
            .map(this::toDTO)
            .toList();
    }

    // metodo de update que verifica se o usuário é dono da tarefa para atualizar
    public TaskDTO update(UUID id, TaskDTO dto){
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));

        validateOwnership(task);

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setCompleted(dto.completed());

        return toDTO(taskRepository.save(task));
    }

    // Metodo de delete verifica se user é dono da tarefa para poder excluir
    public void delete(UUID id){
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));

        validateOwnership(task);
        taskRepository.delete(task);
    }

    // =============== Melhorar a organização do código, separando as responsabilidades em métodos distintos ================

    private UserModel getAuthenticatedUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    // validar se pertence ao usuário logado
    private void validateOwnership(TaskModel task) {
        UserModel user = getAuthenticatedUser();
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Acesso negado: você não é o proprietário desta tarefa.");
        }
    }

    // converte TaskModel para taskDTO 
    private TaskDTO toDTO(TaskModel task){
        return new TaskDTO(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.isCompleted(),
            task.getUser().getId()
        );
    }
}
