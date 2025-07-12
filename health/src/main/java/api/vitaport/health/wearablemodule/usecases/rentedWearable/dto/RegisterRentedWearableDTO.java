package api.vitaport.health.wearablemodule.usecases.rentedWearable.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record RegisterRentedWearableDTO(
        String employee_registration_number,
        String wearable_identification,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startDatetime
) {
}
