package backend.application.DTO;

import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO (
    @NotBlank String email,
    @NotBlank String name,
    @NotBlank String password
){}
