package api.vitaport.health.wearablemodule.usecases.rentedWearable.dto;

import java.time.LocalDateTime;

public record RegisterRentedWearableDTO(
        String employee_registration_number,
        String wearable_identification,
        LocalDateTime startDatetime
) {
}
