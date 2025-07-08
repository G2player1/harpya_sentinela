package api.vitaport.health.gpsmodule.usecases.zone.dto;

import api.vitaport.health.gpsmodule.domain.models.RestrictedEmployee;
import api.vitaport.health.gpsmodule.domain.models.Zone;
import api.vitaport.health.healthmodule.usecases.employee.dto.ReadEmployeeDTO;
import org.locationtech.jts.geom.Polygon;

import java.util.List;
import java.util.UUID;

public record CreatedZoneDTO(
        UUID zone_id,
        String name,
        String description,
        Polygon area,
        List<ReadEmployeeDTO> employees
) {
    public CreatedZoneDTO(Zone zone){
        this(zone.getId(), zone.getName(), zone.getDescription(), zone.getArea(),
                zone.getRestrictedEmployees().stream().map(RestrictedEmployee::getEmployee).map(ReadEmployeeDTO::new).toList());
    }
}
