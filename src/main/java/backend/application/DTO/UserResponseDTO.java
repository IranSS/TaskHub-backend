package backend.application.DTO;

import backend.application.models.user.UserModel;
import backend.application.models.user.UserRole;
import jakarta.validation.constraints.NotBlank;

public record UserResponseDTO(
    @NotBlank
    String email,
    @NotBlank
    String name,
    @NotBlank
    String password,
    UserRole role
) {

    public static UserResponseDTO from(UserModel user){
        return new UserResponseDTO(
            user.getEmail(), 
            user.getName(), 
            user.getPassword(), 
            user.getRole()
        );
    }
}
