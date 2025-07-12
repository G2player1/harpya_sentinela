package api.vitaport.health.gpsmodule.usecases.zone.dto;

import api.vitaport.health.gpsmodule.domain.models.RestrictedEmployee;
import api.vitaport.health.gpsmodule.domain.models.Zone;
import api.vitaport.health.healthmodule.usecases.employee.dto.ReadEmployeeDTO;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public record CreatedZoneDTO(
        UUID zone_id,
        String name,
        String description,
        List<PointDTO> points,
        List<ReadEmployeeDTO> employees
) {
    public CreatedZoneDTO(Zone zone){
        this(zone.getId(), zone.getName(), zone.getDescription(), getPoints(zone),
                zone.getRestrictedEmployees().stream().map(RestrictedEmployee::getEmployee).map(ReadEmployeeDTO::new).toList());
    }

    private static List<PointDTO> getPoints(Zone zone){
        return Arrays.stream(zone.getArea().getCoordinates())
                .map(c -> new PointDTO(c.y, c.x,c.z)) // y = latitude, x = longitude
                .toList();
    }
}
