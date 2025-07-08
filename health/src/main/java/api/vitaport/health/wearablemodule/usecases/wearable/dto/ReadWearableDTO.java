package api.vitaport.health.wearablemodule.usecases.wearable.dto;

import api.vitaport.health.wearablemodule.domain.models.wearable.Wearable;
import api.vitaport.health.wearablemodule.domain.models.wearable.WearableType;

import java.util.UUID;

public record ReadWearableDTO(
        UUID wearable_id,
        String identification,
        String ip,
        WearableType wearableType
) {
    public ReadWearableDTO(Wearable wearable){
        this(wearable.getUuid(), wearable.getIdentification(), wearable.getIp(), wearable.getWearableType());
    }
}
