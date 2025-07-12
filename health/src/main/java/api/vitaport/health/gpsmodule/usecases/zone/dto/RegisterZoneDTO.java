package api.vitaport.health.gpsmodule.usecases.zone.dto;

import java.util.List;
import java.util.UUID;

public record RegisterZoneDTO(
        String name,
        String description,
        List<PointDTO> points,
        List<UUID> employees
) {
}
