package api.vitaport.health.gpsmodule.usecases.employeeLoc;

import api.vitaport.health.gpsmodule.usecases.employeeLoc.dto.EmployeeLocDTO;
import api.vitaport.health.gpsmodule.usecases.employeeLoc.validations.EmployeeLocValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckEmployeeLocUsecase {

    private final EmployeeLocSSEService employeeLocSSEService;
    private final List<EmployeeLocValidation> employeeLocValidations;

    @Autowired
    public CheckEmployeeLocUsecase(EmployeeLocSSEService employeeLocSSEService, List<EmployeeLocValidation> employeeLocValidations){
        this.employeeLocSSEService = employeeLocSSEService;
        this.employeeLocValidations = employeeLocValidations;
    }

    public void execute(EmployeeLocDTO employeeLocDTO){
        employeeLocValidations.forEach(validation -> validation.validate(employeeLocDTO));
        employeeLocSSEService.sendEmployeeLoc(employeeLocDTO);
    }
}
