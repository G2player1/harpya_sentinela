package api.vitaport.health.commonmodule.infra.exceptions;

public class CannotSaveEntityException extends RestResponseException {
    public CannotSaveEntityException(Integer code, ErrorEnum type,String message) {
        super(type,code,message);
    }
}
