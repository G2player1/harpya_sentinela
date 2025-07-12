package api.vitaport.health.usermodule.infra.exceptions;

import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.commonmodule.infra.exceptions.RestResponseException;

public class InvalidTokenException extends RestResponseException {
    public InvalidTokenException(Integer code, ErrorEnum errorEnum, String message) {
        super(errorEnum,code,message);
    }
}
