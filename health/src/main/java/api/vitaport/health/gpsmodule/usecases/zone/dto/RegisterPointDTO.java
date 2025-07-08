package api.vitaport.health.gpsmodule.usecases.zone.dto;

public record RegisterPointDTO(
        Double latitude,
        Double longitude,
        Double altitude
) {
}
