package api.vitaport.health.healthmodule.usecases.wearable.dto;

import api.vitaport.health.healthmodule.domain.models.wearable.WearableType;

public record RegisterWearableDTO(
        String identification,
        String ip,
        WearableType wearableType
) {
}
