package api.vitaport.health.healthmodule.mappers;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.healthmodule.usecases.employee.dto.CreatedEmployeeDTO;
import api.vitaport.health.healthmodule.usecases.employee.dto.ReadEmployeeDTO;
import api.vitaport.health.healthmodule.usecases.employee.dto.RegisterEmployeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeMapper {


    public Employee mapToEmployeeEntity(RegisterEmployeeDTO registerEmployeeDTO){
        return new Employee(registerEmployeeDTO.cpf(), registerEmployeeDTO.fullName(),
                registerEmployeeDTO.registrationNumber(), registerEmployeeDTO.socialName());
    }

    public CreatedEmployeeDTO mapToCreatedEmployeeDTO(Employee employee){
        return new CreatedEmployeeDTO(employee);
    }

    public ReadEmployeeDTO mapToReadEmployeeDTO(Employee employee){
        return new ReadEmployeeDTO(employee);
    }

    public List<ReadEmployeeDTO> mapToReadEmployeeDTOList(List<Employee> employees){
        return employees.stream().map(ReadEmployeeDTO::new).toList();
    }

    public Page<ReadEmployeeDTO> mapToReadEmployeeDTOPage(Page<Employee> employees){
        return employees.map(ReadEmployeeDTO::new);
    }
}
