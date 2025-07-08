package api.vitaport.health.gpsmodule.mapper;

import api.vitaport.health.gpsmodule.domain.models.RestrictedEmployee;
import api.vitaport.health.gpsmodule.domain.models.Zone;
import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import org.springframework.stereotype.Service;

@Service
public class RestrictedEmployeeMapper {

    public RestrictedEmployee mapToEntity(Zone zone, Employee employee){
        return new RestrictedEmployee(zone,employee);
    }
}
