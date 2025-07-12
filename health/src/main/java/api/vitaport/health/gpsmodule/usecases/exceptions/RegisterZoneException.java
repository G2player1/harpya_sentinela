package api.vitaport.health.gpsmodule.usecases.exceptions;

import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.commonmodule.infra.exceptions.RestResponseException;

public class RegisterZoneException extends RestResponseException {
    public RegisterZoneException(Integer code, ErrorEnum errorEnum, String message) {
        super(errorEnum,code,message);
    }
}
