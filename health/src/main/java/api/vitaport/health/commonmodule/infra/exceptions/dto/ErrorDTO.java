package api.vitaport.health.commonmodule.infra.exceptions.dto;


import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.commonmodule.infra.exceptions.RestResponseException;

public record ErrorDTO(
        String error,
        ErrorEnum type,
        Integer code,
        String message
) {
    public ErrorDTO(RestResponseException e){
        this(e.getClass().getSimpleName(), e.getType(), e.getCode(), e.getMessage());
    }
}
