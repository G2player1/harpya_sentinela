package api.vitaport.health.healthmodule.usecases.healthdata.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonIgnoreProperties
public record RegisterHealthDataDTO(
        @NotNull
        UUID wearable_id,
        @NotNull
        Double rawRedPPG,
        @NotNull
        Double rawIRPPG,
        @NotNull
        Double rawECG,
        @NotNull
        Double heartRate,
        @NotNull
        Double spo2,
        @NotNull
        Double hrvSDNN,
        @NotNull
        Double hrvRMSSD,
        @NotNull
        Boolean fallDetected,
        @NotNull
        Double skinTemp,
        @NotNull
        LocalDateTime timestamp,
        @NotNull
        Double amplitude,
        @NotNull
                @NotBlank
        String quality,
        @NotNull
                @NotBlank
        String consistence
) {
}
