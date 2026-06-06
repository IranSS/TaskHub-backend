package backend.application.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.application.DTO.task.TaskDTO;
import backend.application.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Criar uma nova tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa Criada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.create(dto));
    }

    @Operation(summary = "buscar tarefa por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    // operação para obter uma lista de tarefas pelo id
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskId(@PathVariable UUID id) {
        return ResponseEntity.ok(taskService.findByID(id));
    }

    @Operation(summary = "Listar tarefas do usuário autenticado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de tarefas")
    })
    @GetMapping
    public ResponseEntity<List<TaskDTO>> findByUser() {
        return ResponseEntity.ok(taskService.findByAuthenticatedUser());
    }

    @Operation(summary = "Atualizar tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    // operação para atualizar uma tarefa existente
    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> update(@PathVariable UUID id, @RequestBody TaskDTO entity) {
        return ResponseEntity.ok(taskService.update(id, entity));
    }

    @Operation(summary = "Deletar uma tarefa")
     @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa deletada"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
