package api.vitaport.health.usermodule.infra.exceptions;

import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEnumException;
import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("api.vitaport.health.usermodule")
public class UserErrorHandler {

    public record ErrorDTO(String type,String error){
    }

    @ExceptionHandler(CreateTokenException.class)
    public ResponseEntity<ErrorDTO> handleCreationTokenException(CreateTokenException e){
        return ResponseEntity.internalServerError().body(new ErrorDTO(e.getClass().getName(),e.getMessage()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorDTO> handleInvalidTokenException(InvalidTokenException e){
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getClass().getName(),e.getMessage()));
    }

    @ExceptionHandler(CannotGetEntityDataException.class)
    public ResponseEntity<ErrorDTO> handleCantGetEntityDataException(CannotGetEntityDataException e){
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getClass().getName(), e.getMessage()));
    }

    @ExceptionHandler(CannotGetEnumException.class)
    public ResponseEntity<ErrorDTO> handleCantGetEnumException(CannotGetEntityDataException e){
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getClass().getName(), e.getMessage()));
    }

    @ExceptionHandler(UserRegisterValidationException.class)
    public ResponseEntity<ErrorDTO> handleUserRegisterValidationException(UserRegisterValidationException e){
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getClass().getName(), e.getMessage()));
    }
}
