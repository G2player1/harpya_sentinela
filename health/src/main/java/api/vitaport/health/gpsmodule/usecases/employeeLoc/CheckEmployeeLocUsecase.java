package api.vitaport.health.gpsmodule.usecases.employeeLoc;

import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.gpsmodule.domain.models.RestrictedEmployee;
import api.vitaport.health.gpsmodule.domain.models.Zone;
import api.vitaport.health.gpsmodule.infra.repositories.IZoneRepository;
import api.vitaport.health.gpsmodule.mapper.EmployeeLocMapper;
import api.vitaport.health.gpsmodule.usecases.employeeLoc.dto.AlertEmployeeLocDTO;
import api.vitaport.health.gpsmodule.usecases.employeeLoc.dto.EmployeeLocDTO;
import api.vitaport.health.gpsmodule.usecases.exceptions.CheckEmployeeLocException;
import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.healthmodule.usecases.employee.GetEmployeeDataByRegistrationNumberUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckEmployeeLocUsecase {

    private final IZoneRepository zoneRepository;
    private final EmployeeLocMapper employeeLocMapper;
    private final EmployeeLocSSEService employeeLocSSEService;
    private final GetEmployeeDataByRegistrationNumberUsecase getEmployeeDataByRegistrationNumberUsecase;

    @Autowired
    public CheckEmployeeLocUsecase(IZoneRepository zoneRepository,
                                   EmployeeLocMapper employeeLocMapper,
                                   EmployeeLocSSEService employeeLocSSEService,
                                   GetEmployeeDataByRegistrationNumberUsecase getEmployeeDataByRegistrationNumberUsecase){
        this.zoneRepository = zoneRepository;
        this.employeeLocMapper = employeeLocMapper;
        this.employeeLocSSEService = employeeLocSSEService;
        this.getEmployeeDataByRegistrationNumberUsecase = getEmployeeDataByRegistrationNumberUsecase;
    }

    public void execute(EmployeeLocDTO employeeLocDTO){
        Employee employee = getEmployeeDataByRegistrationNumberUsecase.execute(employeeLocDTO.registration_number());
        if (employee == null)
            throw new CheckEmployeeLocException(400, ErrorEnum.LAPI, "employee does not exists");
        List<Zone> zones = zoneRepository.findZonesContainingPoint(employeeLocDTO.longitude(), employeeLocDTO.latitude());
        for (Zone zone : zones){
            for (RestrictedEmployee restrictedEmployee : zone.getRestrictedEmployees()){
                if (restrictedEmployee.getEmployee().getRegistrationNumber().equalsIgnoreCase(employee.getRegistrationNumber()) && restrictedEmployee.getActive()){
                    AlertEmployeeLocDTO alertEmployeeLocDTO = employeeLocMapper.mapToAlertDTO(employee,employeeLocDTO,"employee has invaded a restrict area for him");
                    employeeLocSSEService.sendAlertEmployeeLoc(alertEmployeeLocDTO);
                }
            }
        }
    }
}
