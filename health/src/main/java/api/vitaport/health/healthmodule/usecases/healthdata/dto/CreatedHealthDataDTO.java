package api.vitaport.health.healthmodule.usecases.healthdata.dto;

import api.vitaport.health.healthmodule.domain.models.healthdata.HealthData;
import api.vitaport.health.healthmodule.domain.models.healthdata.StressLevel;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreatedHealthDataDTO(
        UUID healthdata_id,
        Double heartRate,
        Double spo2,
        Double hrvRMSSD,
        Double hrvSDNN,
        StressLevel stressLevel,
        Double skinTemp,
        LocalDateTime timestamp,
        UUID employeeId
) {

    public CreatedHealthDataDTO(HealthData healthData){
        this(healthData.getId(), healthData.getHeartRate(), healthData.getSpo2(),
                healthData.getHrvRMSSD(), healthData.getHrvSDNN(), healthData.getStressLevel(),
                healthData.getSkinTemp(), healthData.getTimestamp(), healthData.getEmployee().getId());
    }
}
