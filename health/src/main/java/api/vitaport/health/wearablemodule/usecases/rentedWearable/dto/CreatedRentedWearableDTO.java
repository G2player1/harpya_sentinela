package api.vitaport.health.wearablemodule.usecases.rentedWearable.dto;

import api.vitaport.health.wearablemodule.domain.models.wearable.RentedWearable;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreatedRentedWearableDTO(
        UUID rented_wearable_id,
        UUID employee_id,
        UUID wearable_id,
        LocalDateTime startDatetime,
        Boolean inUse
) {
    public CreatedRentedWearableDTO(RentedWearable rentedWearable){
        this(rentedWearable.getId(), rentedWearable.getEmployee().getId(), rentedWearable.getWearable().getUuid(),rentedWearable.getStartDatetime(), rentedWearable.getInUse());
    }
}
