package api.vitaport.health.wearablemodule.usecases.wearable.dto;

public record RegisterWearableDTO(
        String identification,
        String ip,
        String wearableType
) {
}
