package api.vitaport.health.commonmodule.infra.exceptions;

import api.vitaport.health.commonmodule.infra.exceptions.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CannotGetEnumException.class)
    public ResponseEntity<ErrorDTO> handle(CannotGetEnumException e){
        ErrorDTO errorDTO = new ErrorDTO(e);
        return ResponseEntity.status(errorDTO.code()).body(errorDTO);
    }

    @ExceptionHandler(CannotGetEntityDataException.class)
    public ResponseEntity<ErrorDTO> handle(CannotGetEntityDataException e){
        ErrorDTO errorDTO = new ErrorDTO(e);
        return ResponseEntity.status(errorDTO.code()).body(errorDTO);
    }

    @ExceptionHandler(CannotSaveEntityException.class)
    public ResponseEntity<ErrorDTO> handle(CannotSaveEntityException e){
        ErrorDTO errorDTO = new ErrorDTO(e);
        return ResponseEntity.status(errorDTO.code()).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDTO>> handle(MethodArgumentNotValidException e) {
        List<ErrorDTO> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ErrorDTO(
                        error.getClass().getSimpleName(),
                        ErrorEnum.GAPI,
                        400,
                        error.getDefaultMessage()
                ))
                .toList();

        return ResponseEntity.status(400).body(errors);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorDTO> handle(AuthorizationDeniedException e){
        ErrorDTO errorDTO = new ErrorDTO(e.getClass().getSimpleName(),ErrorEnum.GAPI, 401, "Unauthorized access");
        return ResponseEntity.status(errorDTO.code()).body(errorDTO);
    }

}
