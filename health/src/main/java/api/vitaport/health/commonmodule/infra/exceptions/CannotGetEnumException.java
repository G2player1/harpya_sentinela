package api.vitaport.health.commonmodule.infra.exceptions;

public class CannotGetEnumException extends RestResponseException {
    public CannotGetEnumException(Integer code, ErrorEnum type,String message) {
        super(type,code,message);
    }
}
