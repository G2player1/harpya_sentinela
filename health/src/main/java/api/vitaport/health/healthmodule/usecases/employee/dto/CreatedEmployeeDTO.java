package api.vitaport.health.healthmodule.usecases.employee.dto;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;

import java.util.UUID;

public record CreatedEmployeeDTO(
        UUID id,
        UUID user_id,
        String registrationNumber,
        String cpf,
        String fullName,
        String socialName
) {
    public CreatedEmployeeDTO(Employee employee){
        this(employee.getId(), employee.getUser().getId(), employee.getRegistrationNumber(), employee.getCpf(),
                employee.getFullName(), employee.getSocialName());
    }
}
