package backend.application.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(){
        super("Credenciais inválidas");
    }
}
