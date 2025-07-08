package api.vitaport.health.wearablemodule.usecases.rentedWearable.validations;

import api.vitaport.health.wearablemodule.usecases.rentedWearable.dto.RegisterRentedWearableDTO;

public interface IRegisterRentedWearableValidation {

    void validate(RegisterRentedWearableDTO registerRentedWearableDTO);
}
