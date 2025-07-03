package api.vitaport.health.wearablemodule.usecases.wearable.dto;

import api.vitaport.health.wearablemodule.domain.models.wearable.WearableType;

public record RegisterWearableDTO(
        String identification,
        String ip,
        WearableType wearableType
) {
}
