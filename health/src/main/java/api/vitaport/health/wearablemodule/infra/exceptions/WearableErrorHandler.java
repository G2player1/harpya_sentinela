package api.vitaport.health.wearablemodule.infra.exceptions;

import api.vitaport.health.commonmodule.infra.exceptions.dto.ErrorDTO;
import api.vitaport.health.wearablemodule.usecases.exceptions.RentWearableValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("api.vitaport.wearablemodule")
public class WearableErrorHandler {


    @ExceptionHandler(RentWearableValidationException.class)
    public ResponseEntity<ErrorDTO> handle(RentWearableValidationException e){
        ErrorDTO errorDTO = new ErrorDTO(e);
        return ResponseEntity.status(errorDTO.code()).body(errorDTO);
    }
}
