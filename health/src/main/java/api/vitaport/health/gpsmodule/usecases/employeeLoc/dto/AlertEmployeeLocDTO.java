package api.vitaport.health.gpsmodule.usecases.employeeLoc.dto;

import api.vitaport.health.healthmodule.usecases.employee.dto.ReadEmployeeDTO;

public record AlertEmployeeLocDTO(
        String message,
        ReadEmployeeDTO employee,
        Double Altitude,
        Double longitude,
        Double latitude
) {
}
