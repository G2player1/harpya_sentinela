package api.vitaport.health.wearablemodule.usecases.rentedWearable.validations;

import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.healthmodule.infra.repositories.IEmployeeRepository;
import api.vitaport.health.wearablemodule.domain.models.wearable.Wearable;
import api.vitaport.health.wearablemodule.infra.repositories.IRentedWearableRepository;
import api.vitaport.health.wearablemodule.infra.repositories.IWearableRepository;
import api.vitaport.health.wearablemodule.usecases.exceptions.RentWearableValidationException;
import api.vitaport.health.wearablemodule.usecases.rentedWearable.dto.RegisterRentedWearableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlreadyRentedWearableValidation extends RegisterRentedWearableValidation{

    @Autowired
    public AlreadyRentedWearableValidation(IEmployeeRepository employeeRepository, IWearableRepository wearableRepository, IRentedWearableRepository rentedWearableRepository) {
        super(employeeRepository, wearableRepository, rentedWearableRepository);
    }

    @Override
    public void validate(RegisterRentedWearableDTO registerRentedWearableDTO) {
        Wearable wearable = wearableRepository.getWearableByIdentification(registerRentedWearableDTO.wearable_identification());
        if (wearable.getRentedWearable().getInUse() == false)
            throw new RentWearableValidationException(400, ErrorEnum.LAPI, "this wearable is in use");
    }
}
