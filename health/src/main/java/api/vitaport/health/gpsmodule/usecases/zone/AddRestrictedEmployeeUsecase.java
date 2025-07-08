package api.vitaport.health.gpsmodule.usecases.zone;

import api.vitaport.health.gpsmodule.domain.models.RestrictedEmployee;
import api.vitaport.health.gpsmodule.domain.models.Zone;
import api.vitaport.health.gpsmodule.infra.repositories.IZoneRepository;
import api.vitaport.health.gpsmodule.mapper.RestrictedEmployeeMapper;
import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.healthmodule.usecases.employee.GetEmployeeDataUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AddRestrictedEmployeeUsecase {

    private final IZoneRepository zoneRepository;
    private final GetZoneByNameUsecase getZoneByNameUsecase;
    private final GetEmployeeDataUsecase getEmployeeDataUsecase;
    private final RestrictedEmployeeMapper restrictedEmployeeMapper;

    @Autowired
    public AddRestrictedEmployeeUsecase(IZoneRepository zoneRepository, GetZoneByNameUsecase getZoneByNameUsecase,
                                        GetEmployeeDataUsecase getEmployeeDataUsecase, RestrictedEmployeeMapper restrictedEmployeeMapper){
        this.zoneRepository = zoneRepository;
        this.getZoneByNameUsecase = getZoneByNameUsecase;
        this.getEmployeeDataUsecase = getEmployeeDataUsecase;
        this.restrictedEmployeeMapper = restrictedEmployeeMapper;
    }

    @Transactional
    public Zone execute(String zoneName, UUID employeeId){
        Zone zone = getZoneByNameUsecase.execute(zoneName);
        Employee employee = getEmployeeDataUsecase.execute(employeeId);
        RestrictedEmployee restrictedEmployee = restrictedEmployeeMapper.mapToEntity(zone, employee);
        zone.addRestrictedEmployee(restrictedEmployee);
        return zoneRepository.save(zone);
    }
}
