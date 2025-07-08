package api.vitaport.health.gpsmodule.mapper;

import api.vitaport.health.gpsmodule.usecases.employeeLoc.dto.AlertEmployeeLocDTO;
import api.vitaport.health.gpsmodule.usecases.employeeLoc.dto.EmployeeLocDTO;
import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.healthmodule.mappers.EmployeeMapper;
import api.vitaport.health.healthmodule.usecases.employee.dto.ReadEmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeLocMapper {

    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeLocMapper(EmployeeMapper employeeMapper){
        this.employeeMapper = employeeMapper;
    }

    public AlertEmployeeLocDTO mapToAlertDTO(Employee employee, EmployeeLocDTO employeeLocDTO, String message){
        ReadEmployeeDTO readEmployeeDTO = employeeMapper.mapToReadEmployeeDTO(employee);
        return new AlertEmployeeLocDTO(message,readEmployeeDTO, employeeLocDTO.altitude(), employeeLocDTO.longitude(), employeeLocDTO.latitude());
    }
}
