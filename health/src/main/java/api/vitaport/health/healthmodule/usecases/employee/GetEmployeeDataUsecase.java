package api.vitaport.health.healthmodule.usecases.employee;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.healthmodule.infra.repositories.IEmployeeRepository;
import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetEmployeeDataUsecase {

    private final IEmployeeRepository employeeRepository;

    @Autowired
    public GetEmployeeDataUsecase(IEmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Employee execute(UUID id){
        try {
            return employeeRepository.getReferenceById(id);
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ,"cant get employee by uuid");
        }
    }
}
