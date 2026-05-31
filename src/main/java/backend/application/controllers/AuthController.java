package backend.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.application.DTO.AuthResponsedDTO;
import backend.application.DTO.LoginDTO;
import backend.application.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    // Validar sessão, retorna um token para ser usado nos Endpoints
    @Operation(summary = "Autenticação de usuário e geração de token JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponsedDTO> login(
            @RequestBody @Valid LoginDTO loginDTO){
        
        return ResponseEntity.ok(authService.login(loginDTO));
    }
}
