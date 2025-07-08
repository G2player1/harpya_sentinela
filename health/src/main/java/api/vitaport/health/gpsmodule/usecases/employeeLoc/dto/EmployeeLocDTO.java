package api.vitaport.health.gpsmodule.usecases.employeeLoc.dto;


public record EmployeeLocDTO(
        String registration_number,
        Double longitude,
        Double latitude,
        Double altitude
) {
}
