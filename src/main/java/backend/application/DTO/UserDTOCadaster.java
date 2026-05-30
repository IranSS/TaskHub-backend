package backend.application.DTO;

import backend.application.models.user.UserRole;
import jakarta.validation.constraints.NotBlank;

public record UserDTOCadaster(
    @NotBlank
    String email,
    @NotBlank
    String name,
    @NotBlank
    String password,
    UserRole role
) {}
