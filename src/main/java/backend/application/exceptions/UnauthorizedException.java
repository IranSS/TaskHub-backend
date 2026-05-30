package backend.application.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message){
        super(message);
    }
}
