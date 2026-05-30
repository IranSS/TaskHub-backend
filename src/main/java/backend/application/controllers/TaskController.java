package backend.application.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.application.DTO.task.TaskDTO;
import backend.application.models.TaskModel;
import backend.application.models.user.UserModel;
import backend.application.repositories.TaskRepository;
import backend.application.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    UserRepository userRepository;
    TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // operações de CRUD para tarefas
    // operação para criar uma nova tarefa
    @Operation(summary = "Responsável por criar a tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping("/create")
    public ResponseEntity<?> createTask(@RequestBody TaskDTO entity) {
        TaskModel task = new TaskModel();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // procurar usuário pelo email pego do Token
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        task.setTitle(entity.title());
        task.setDescription(entity.description());
        task.setCompleted(entity.completed());
        task.setUser(user);

        taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.OK).body("Tarefa criada com sucesso!");
    }

    @Operation(summary = "Operação para obter uma lista de tarefas pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    // operação para obter uma lista de tarefas pelo id
    @GetMapping("/getOne")
    public ResponseEntity<?> getTaskId(@RequestParam UUID id) {
        return ResponseEntity.status(HttpStatus.FOUND).body(taskRepository.findById(id));
    }

    @Operation(summary = "Pega todas as tarefas de um usuário especifico")
    // Pega todas as tarefas de um usuário especifico
    @GetMapping("/getByUser")
    public List<TaskDTO> getTasksByUser() {
        // Pega o e-mail pelo Token
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        return taskRepository.findByUserId(user.getId()).stream().map(task -> new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                user.getId())).toList();
    }

    @Operation(summary = "Pega todas as tarefas independente do ID do usuário")
    // pegar todas as tarefas
    @GetMapping("/getAll")
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(task -> new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getId())).toList();
    }
     @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @Operation(summary = "Operação para atualizar uma tarefa existente")
    // operação para atualizar uma tarefa existente
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateTask(@PathVariable UUID id, @RequestBody TaskDTO entity) {
        TaskModel task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada!"));

        task.setTitle(entity.title());
        task.setDescription(entity.description());
        task.setCompleted(entity.completed());
        taskRepository.save(task);

        return ResponseEntity.status(HttpStatus.OK).body("Tarefa atualizada com sucesso!");
    }

     @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @Operation(summary = "Deletar uma tarefa pelo id")
    // Deletar uma tarefa pelo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable UUID id) {
        // tarefa precisa existir para ser apagada
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada!");
        }
        taskRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Tarefa deletada com sucesso!");
    }
}
