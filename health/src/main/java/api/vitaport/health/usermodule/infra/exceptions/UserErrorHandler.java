package api.vitaport.health.usermodule.infra.exceptions;

import api.vitaport.health.commonmodule.infra.exceptions.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("api.vitaport.health.usermodule")
public class UserErrorHandler {

    @ExceptionHandler(CreateTokenException.class)
    public ResponseEntity<ErrorDTO> handle(CreateTokenException e){
        ErrorDTO errorDTO = new ErrorDTO(e);
        return ResponseEntity.status(errorDTO.code()).body(errorDTO);
    }

    @ExceptionHandler(UserRegisterValidationException.class)
    public ResponseEntity<ErrorDTO> handle(UserRegisterValidationException e){
        ErrorDTO errorDTO = new ErrorDTO(e);
        return ResponseEntity.status(errorDTO.code()).body(errorDTO);
    }
}
