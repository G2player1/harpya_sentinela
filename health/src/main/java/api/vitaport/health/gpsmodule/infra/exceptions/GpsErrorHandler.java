package api.vitaport.health.gpsmodule.infra.exceptions;

import api.vitaport.health.commonmodule.infra.exceptions.dto.ErrorDTO;
import api.vitaport.health.gpsmodule.usecases.exceptions.CheckEmployeeLocException;
import api.vitaport.health.gpsmodule.usecases.exceptions.RegisterZoneException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("api.vitaport.health.gpsmodule")
public class GpsErrorHandler {

    @ExceptionHandler(CheckEmployeeLocException.class)
    public ResponseEntity<ErrorDTO> handle(CheckEmployeeLocException e){
        ErrorDTO errorDTO = new ErrorDTO(e);
        return ResponseEntity.status(errorDTO.code()).body(errorDTO);
    }

    @ExceptionHandler(RegisterZoneException.class)
    public ResponseEntity<ErrorDTO> handle(RegisterZoneException e){
        ErrorDTO errorDTO = new ErrorDTO(e);
        return ResponseEntity.status(errorDTO.code()).body(errorDTO);
    }
}
