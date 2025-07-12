package api.vitaport.health.gpsmodule.usecases.zone.dto;

public record PointDTO(
        Double latitude,
        Double longitude,
        Double altitude
) {
}
