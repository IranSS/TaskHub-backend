package backend.application.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import backend.application.DTO.AuthResponsedDTO;
import backend.application.DTO.LoginDTO;
import backend.application.config.Token.JwtService;
import backend.application.exceptions.InvalidCredentialsException;
import backend.application.exceptions.ResourceNotFoundException;
import backend.application.models.user.UserModel;
import backend.application.repositories.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
        UserRepository userRepository,
        JwtService jwtService,
        PasswordEncoder passwordEncoder
    ){
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponsedDTO login(LoginDTO dto){
        UserModel user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> 
                        new ResourceNotFoundException("Usuário não encontrado"));

        if(!passwordEncoder.matches(dto.password(),  user.getPassword())){
            throw new InvalidCredentialsException();
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponsedDTO(token);
    }
}
