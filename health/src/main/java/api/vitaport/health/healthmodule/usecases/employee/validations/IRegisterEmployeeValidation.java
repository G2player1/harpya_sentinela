package api.vitaport.health.healthmodule.usecases.employee.validations;

import api.vitaport.health.healthmodule.usecases.employee.dto.RegisterEmployeeDTO;
import org.springframework.stereotype.Service;

@Service
public interface IRegisterEmployeeValidation {

    void validate(RegisterEmployeeDTO registerEmployeeDTO);
}
