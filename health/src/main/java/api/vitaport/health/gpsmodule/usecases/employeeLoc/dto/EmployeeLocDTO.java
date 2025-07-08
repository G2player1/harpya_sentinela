package api.vitaport.health.gpsmodule.usecases.employeeLoc.dto;

import org.locationtech.jts.geom.Point;

public record EmployeeLocDTO(
        String registration_number,
        Point point
) {
}
