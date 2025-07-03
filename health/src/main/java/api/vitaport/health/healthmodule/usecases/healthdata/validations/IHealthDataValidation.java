package api.vitaport.health.healthmodule.usecases.healthdata.validations;

import api.vitaport.health.healthmodule.usecases.healthdata.dto.RegisterHealthDataDTO;
import org.springframework.stereotype.Service;

@Service
public interface IHealthDataValidation {

    void validate(RegisterHealthDataDTO registerHealthDataDTO);
}
