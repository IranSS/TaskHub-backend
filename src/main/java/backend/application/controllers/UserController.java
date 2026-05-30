package backend.application.controllers;

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

import backend.application.DTO.UserDTOCadaster;
import backend.application.models.user.UserModel;
import backend.application.models.user.UserRole;
import backend.application.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/users")
public class UserController {
    
    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CRUD completo de usuários
    // operação para criar um novo usuário
    @Operation(summary = "Responsável por criar um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User Created")
    })
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserDTOCadaster entity) {
        UserModel user = new UserModel();
        user.setEmail(entity.email());
        user.setName(entity.name());
        user.setPassword(entity.password());

        // Se o usuário não informar seu nível de acesso, ele vai ser criado como um
        // usuário padrão
        // Atualmente só existe 2 tipos USER(o padrão) e ADMIN(consegue acessar todos os
        // endpoints)
        if (entity.role() == null) {
            user.setRole(UserRole.USER);
        }
        else if(user.getRole() == null){
            user.setRole(UserRole.USER);
        }
        else {
            user.setRole(entity.role());
        }

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso!");
    }

    @Operation(summary = "Operação para obter um usuário pelo email")
    // operação para obter um usuário pelo email
    @GetMapping("/getOne")
    public ResponseEntity<?> getOneUser(@RequestParam String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findByEmail(email));
    }

    @Operation(summary = "Operação para obter todos os usuários")
    // operação para obter todos os usuários
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @Operation(summary = "Atulizar um usuário pelo id")
    // atulizar um usuário pelo id
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody UserDTOCadaster entity) {
        // procura o usuário pelo id, se não encontrar, lança uma execeção
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        user.setEmail(entity.email());
        user.setName(entity.name());
        user.setPassword(entity.password());
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body("Usuário atualizado com sucesso!");
    }

    @Operation(summary = "Deletar usuário pelo id")
    // Deletar usuário pelo id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        // Verifica se o usuário existe
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado!");
        }
        // Deletar o usuário se encontrar ele
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário deletado com sucesso!");
    }
}
