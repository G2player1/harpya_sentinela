package api.vitaport.health.wearablemodule.usecases.rentedWearable.dto;

import api.vitaport.health.healthmodule.usecases.employee.dto.ReadEmployeeDTO;
import api.vitaport.health.wearablemodule.domain.models.wearable.RentedWearable;
import api.vitaport.health.wearablemodule.usecases.wearable.dto.ReadWearableDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReadRentedWearableDTO(
        UUID rented_wearable_id,
        ReadEmployeeDTO employee,
        ReadWearableDTO wearable,
        LocalDateTime startDatetime,
        LocalDateTime endDatetime,
        Boolean inUse
) {
    public ReadRentedWearableDTO(RentedWearable rentedWearable){
        this(rentedWearable.getId(), new ReadEmployeeDTO(rentedWearable.getEmployee()), new ReadWearableDTO(rentedWearable.getWearable()),
                rentedWearable.getStartDatetime(), rentedWearable.getEndDatetime(), rentedWearable.getInUse());
    }
}
