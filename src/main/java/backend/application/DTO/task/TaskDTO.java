package backend.application.DTO.task;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record TaskDTO(
    UUID id, 
    @NotBlank
    String title, 
    @NotBlank
    String description,
     boolean completed, 
     UUID userId) {
    
}
