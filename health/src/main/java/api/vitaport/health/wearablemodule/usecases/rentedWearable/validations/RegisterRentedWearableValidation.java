package api.vitaport.health.wearablemodule.usecases.rentedWearable.validations;

import api.vitaport.health.healthmodule.infra.repositories.IEmployeeRepository;
import api.vitaport.health.wearablemodule.infra.repositories.IRentedWearableRepository;
import api.vitaport.health.wearablemodule.infra.repositories.IWearableRepository;
import api.vitaport.health.wearablemodule.usecases.rentedWearable.dto.RegisterRentedWearableDTO;
import org.springframework.stereotype.Service;

@Service
public abstract class RegisterRentedWearableValidation implements IRegisterRentedWearableValidation{

    protected final IWearableRepository wearableRepository;
    protected final IEmployeeRepository employeeRepository;
    protected final IRentedWearableRepository rentedWearableRepository;

    public RegisterRentedWearableValidation(IEmployeeRepository employeeRepository, IWearableRepository wearableRepository, IRentedWearableRepository rentedWearableRepository) {
        this.employeeRepository = employeeRepository;
        this.wearableRepository = wearableRepository;
        this.rentedWearableRepository = rentedWearableRepository;
    }

    @Override
    public abstract void validate(RegisterRentedWearableDTO registerRentedWearableDTO);
}
