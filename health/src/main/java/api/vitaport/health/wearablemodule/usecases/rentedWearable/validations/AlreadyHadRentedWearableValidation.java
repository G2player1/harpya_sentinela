package api.vitaport.health.wearablemodule.usecases.rentedWearable.validations;

import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.healthmodule.infra.repositories.IEmployeeRepository;
import api.vitaport.health.wearablemodule.infra.repositories.IRentedWearableRepository;
import api.vitaport.health.wearablemodule.infra.repositories.IWearableRepository;
import api.vitaport.health.wearablemodule.usecases.exceptions.RentWearableValidationException;
import api.vitaport.health.wearablemodule.usecases.rentedWearable.dto.RegisterRentedWearableDTO;
import org.springframework.stereotype.Service;

@Service
public class AlreadyHadRentedWearableValidation extends RegisterRentedWearableValidation{

    public AlreadyHadRentedWearableValidation(IEmployeeRepository employeeRepository, IWearableRepository wearableRepository, IRentedWearableRepository rentedWearableRepository) {
        super(employeeRepository, wearableRepository, rentedWearableRepository);
    }

    @Override
    public void validate(RegisterRentedWearableDTO registerRentedWearableDTO) {
        Employee employee = employeeRepository.findEmployeeByRegistration(registerRentedWearableDTO.employee_registration_number());
        if (employee.getRentedWearable().getInUse() == false)
            throw new RentWearableValidationException(400, ErrorEnum.LAPI, "employee already have a rented wearable in use");
    }
}
