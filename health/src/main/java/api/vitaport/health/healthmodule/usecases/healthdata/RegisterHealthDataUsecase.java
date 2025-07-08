package api.vitaport.health.healthmodule.usecases.healthdata;

import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.healthmodule.domain.models.healthdata.HealthData;
import api.vitaport.health.healthmodule.infra.repositories.IEmployeeRepository;
import api.vitaport.health.healthmodule.infra.repositories.IHealthDataRepository;
import api.vitaport.health.healthmodule.mappers.HealthDataMapper;
import api.vitaport.health.healthmodule.usecases.healthdata.dto.ReadHealthDataDTO;
import api.vitaport.health.healthmodule.usecases.healthdata.dto.RegisterHealthDataDTO;
import api.vitaport.health.healthmodule.usecases.healthdata.validations.IRegisterRawHealthDataValidation;
import api.vitaport.health.healthmodule.usecases.sse.HealthSSEService;
import api.vitaport.health.wearablemodule.usecases.rentedWearable.GetActualWearableUserUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterHealthDataUsecase {

    //repositories
    private final IEmployeeRepository employeeRepository;
    private final IHealthDataRepository healthDataRepository;
    //mappers
    private final HealthDataMapper healthDataMapper;
    //usecases
    private final GetActualWearableUserUsecase getActualWearableUserUsecase;
    //services
    private final HealthSSEService healthSSEService;
    //validações
    private final List<IRegisterRawHealthDataValidation> registerRawHealthDataValidations;

    @Autowired
    public RegisterHealthDataUsecase(List<IRegisterRawHealthDataValidation> registerRawHealthDataValidations,
                                     HealthSSEService healthSSEService,
                                     HealthDataMapper healthDataMapper,
                                     IEmployeeRepository employeeRepository,
                                     IHealthDataRepository healthDataRepository,
                                     GetActualWearableUserUsecase getActualWearableUserUsecase){
        this.healthSSEService = healthSSEService;
        this.healthDataMapper = healthDataMapper;
        this.employeeRepository = employeeRepository;
        this.healthDataRepository = healthDataRepository;
        this.getActualWearableUserUsecase = getActualWearableUserUsecase;
        this.registerRawHealthDataValidations = registerRawHealthDataValidations;
    }

    public void execute(RegisterHealthDataDTO registerHealthDataDTO){
        registerRawHealthDataValidations.forEach(validation -> validation.validate(registerHealthDataDTO));
        Employee employee = getActualWearableUserUsecase.execute(registerHealthDataDTO.wearable_id());
        if (employee == null)
            throw new CannotGetEntityDataException(400, ErrorEnum.LAPI,"cant get employee by wearable uuid");
        HealthData healthData = healthDataMapper.mapToEntity(employee,registerHealthDataDTO);
        healthData = healthDataRepository.save(healthData);
        employee.addHealthData(healthData);
        employeeRepository.save(employee);
        ReadHealthDataDTO readHealthDataDTO = healthDataMapper.mapToReadHealthData(healthData);
        healthSSEService.sendHealthData(readHealthDataDTO);
    }
}
