package api.vitaport.health.healthmodule.usecases.healthdata.validations;

import api.vitaport.health.healthmodule.usecases.healthdata.dto.RegisterHealthDataDTO;

public interface IRegisterRawHealthDataValidation {

    void validate(RegisterHealthDataDTO registerHealthDataDTO);
}
