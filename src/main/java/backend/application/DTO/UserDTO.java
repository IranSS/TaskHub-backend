package backend.application.DTO;

import jakarta.validation.constraints.NotBlank;

public record UserDTO(
    @NotBlank
    String email) {
    
}
