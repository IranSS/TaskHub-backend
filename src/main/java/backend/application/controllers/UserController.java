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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.application.DTO.UserCreateDTO;
import backend.application.DTO.UserResponseDTO;
import backend.application.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CRUD completo de usuários
    // operação para criar um novo usuário
    @Operation(summary = "Criar um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody UserCreateDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.create(dto));
    }

    @Operation(summary = "Buscar usuário pelo email")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping
    public ResponseEntity<UserResponseDTO> getOneUser(@RequestParam String email) {
        return ResponseEntity.ok(userService.findbyEmail(email));
    }

    @Operation(summary = "Listar todos os usuários")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description= "Lista de usuários")
    })
    @GetMapping("/all")
    public ResponseEntity<List> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(summary = "Atulizar um usuário pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
                @PathVariable UUID id, 
                @Valid @RequestBody UserResponseDTO dto) {
        
        return ResponseEntity.ok(userService.update(id, dto));
    }

    @Operation(summary = "Deletar usuário pelo id")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuário removido"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "403", description = "Acesso negado") 
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDTO> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
