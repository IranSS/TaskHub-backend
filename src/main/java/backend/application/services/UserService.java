package backend.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import backend.application.DTO.UserResponseDTO;
import backend.application.exceptions.ResourceNotFoundException;
import backend.application.exceptions.UnauthorizedException;
import backend.application.models.user.UserModel;
import backend.application.models.user.UserRole;
import backend.application.repositories.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserResponseDTO create(UserResponseDTO dto){
        UserModel user = new UserModel();

        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setPassword(dto.password());
        user.setRole(dto.role());

        return UserResponseDTO.from(userRepository.save(user));
    }

    public UserResponseDTO findbyEmail(String email){
        return UserResponseDTO.from(
            userRepository.findByEmail(email).orElseThrow(() -> 
                new ResourceNotFoundException("Usuário não encontrado"))
        );
    }

    public List<UserResponseDTO> findAll(){
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::from)
                .toList();
    }

    public UserResponseDTO update(UUID id, UserResponseDTO dto){
        UserModel user = getById(id);
        validateOwnerShipOrAdmin(user);

        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setPassword(dto.password());

        return UserResponseDTO.from(userRepository.save(user));
    }

    public void delete(UUID id){
        UserModel user = getById(id);
        validateOwnerShipOrAdmin(user);
        userRepository.delete(user);
    }

    // ====== 
    private UserModel getById(UUID id){
        return userRepository.findById(id)
                .orElseThrow(() -> 
                        new ResourceNotFoundException("Usuário não encontrado"));
    }

    private void validateOwnerShipOrAdmin(UserModel user){
        String emailLogged = 
                SecurityContextHolder.getContext().getAuthentication().getName();

        if(!user.getEmail().equals(emailLogged)
                    && user.getRole() != UserRole.ADMIN){
                        throw new UnauthorizedException("Acesso negado");
        }
    }
}
