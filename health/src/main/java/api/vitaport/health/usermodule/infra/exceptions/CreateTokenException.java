package api.vitaport.health.usermodule.infra.exceptions;

import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.commonmodule.infra.exceptions.RestResponseException;

public class CreateTokenException extends RestResponseException {
    public CreateTokenException(Integer code, ErrorEnum errorEnum, String message) {
        super(errorEnum,code,message);
    }
}
