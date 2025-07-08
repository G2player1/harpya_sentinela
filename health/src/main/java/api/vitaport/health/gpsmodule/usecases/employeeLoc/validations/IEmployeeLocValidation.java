package api.vitaport.health.gpsmodule.usecases.employeeLoc.validations;

import api.vitaport.health.gpsmodule.usecases.employeeLoc.dto.EmployeeLocDTO;

public interface IEmployeeLocValidation {

    void validate(EmployeeLocDTO employeeLocDTO);
}
