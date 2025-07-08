package api.vitaport.health.wearablemodule.usecases.rentedWearable;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.healthmodule.usecases.employee.GetEmployeeDataByRegistrationNumberUsecase;
import api.vitaport.health.wearablemodule.domain.models.wearable.RentedWearable;
import api.vitaport.health.wearablemodule.domain.models.wearable.Wearable;
import api.vitaport.health.wearablemodule.infra.repositories.IRentedWearableRepository;
import api.vitaport.health.wearablemodule.mappers.RentedWearableMapper;
import api.vitaport.health.wearablemodule.usecases.rentedWearable.dto.RegisterRentedWearableDTO;
import api.vitaport.health.wearablemodule.usecases.rentedWearable.validations.RegisterRentedWearableValidation;
import api.vitaport.health.wearablemodule.usecases.wearable.GetWearableUsecase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterRentedWearableUsecase {

    private final RentedWearableMapper rentedWearableMapper;
    private final IRentedWearableRepository rentedWearableRepository;
    private final List<RegisterRentedWearableValidation> registerRentedWearableValidationList;
    private final GetEmployeeDataByRegistrationNumberUsecase getEmployeeDataByRegistrationNumberUsecase;
    private final GetWearableUsecase getWearableUsecase;

    @Autowired
    public RegisterRentedWearableUsecase(RentedWearableMapper rentedWearableMapper, IRentedWearableRepository rentedWearableRepository,
                                         List<RegisterRentedWearableValidation> registerRentedWearableValidationList,
                                         GetEmployeeDataByRegistrationNumberUsecase getEmployeeDataByRegistrationNumberUsecase,
                                         GetWearableUsecase getWearableUsecase){
        this.rentedWearableMapper = rentedWearableMapper;
        this.rentedWearableRepository = rentedWearableRepository;
        this.registerRentedWearableValidationList = registerRentedWearableValidationList;
        this.getWearableUsecase = getWearableUsecase;
        this.getEmployeeDataByRegistrationNumberUsecase = getEmployeeDataByRegistrationNumberUsecase;
    }

    public RentedWearable execute(RegisterRentedWearableDTO registerRentedWearableDTO){
        registerRentedWearableValidationList.forEach(validation -> validation.validate(registerRentedWearableDTO));
        Employee employee = getEmployeeDataByRegistrationNumberUsecase.execute(registerRentedWearableDTO.employee_registration_number());
        Wearable wearable = getWearableUsecase.getWearableByIdentification(registerRentedWearableDTO.wearable_identification());
        RentedWearable rentedWearable = rentedWearableMapper.mapToEntity(employee,wearable,registerRentedWearableDTO.startDatetime());
        return rentedWearableRepository.save(rentedWearable);
    }

}
