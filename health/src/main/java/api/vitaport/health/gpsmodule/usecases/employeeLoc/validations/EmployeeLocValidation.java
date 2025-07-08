package api.vitaport.health.gpsmodule.usecases.employeeLoc.validations;

import api.vitaport.health.gpsmodule.infra.repositories.IZoneRepository;
import api.vitaport.health.gpsmodule.usecases.employeeLoc.dto.EmployeeLocDTO;
import api.vitaport.health.healthmodule.infra.repositories.IEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class EmployeeLocValidation implements IEmployeeLocValidation{

    protected final IEmployeeRepository employeeRepository;
    protected final IZoneRepository zoneRepository;

    @Autowired
    public EmployeeLocValidation(IEmployeeRepository employeeRepository, IZoneRepository zoneRepository){
        this.employeeRepository = employeeRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public abstract void validate(EmployeeLocDTO employeeLocDTO);
}
