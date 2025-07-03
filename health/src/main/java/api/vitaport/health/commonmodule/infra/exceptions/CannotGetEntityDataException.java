package api.vitaport.health.commonmodule.infra.exceptions;

public class CannotGetEntityDataException extends RestResponseException {
    public CannotGetEntityDataException(Integer code, ErrorEnum errorEnum, String message) {
        super(errorEnum,code,message);
    }
}
