package api.vitaport.health.wearablemodule.usecases.exceptions;

import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.commonmodule.infra.exceptions.RestResponseException;

public class RentWearableValidationException extends RestResponseException {
  public RentWearableValidationException(Integer code, ErrorEnum errorEnum, String message) {
    super(errorEnum,code,message);
  }
}
