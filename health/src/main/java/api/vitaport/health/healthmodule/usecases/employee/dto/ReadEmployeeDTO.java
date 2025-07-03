package api.vitaport.health.healthmodule.usecases.employee.dto;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.healthmodule.usecases.healthdata.dto.ReadHealthDataDTO;
import api.vitaport.health.usermodule.usecases.user.dtos.ReadUserDTO;

import java.util.List;
import java.util.UUID;

public record ReadEmployeeDTO(
        UUID id,
        ReadUserDTO user,
        String registrationNumber,
        String cpf,
        String fullName,
        String socialName,
        List<ReadHealthDataDTO> healthCarePages
) {
    public ReadEmployeeDTO(Employee employee){
        this(employee.getId(), new ReadUserDTO(employee.getUser()), employee.getRegistrationNumber(),
                employee.getCpf(), employee.getFullName(), employee.getSocialName(),
                employee.getHealthDataList().stream().map(ReadHealthDataDTO::new).toList());
    }
}
