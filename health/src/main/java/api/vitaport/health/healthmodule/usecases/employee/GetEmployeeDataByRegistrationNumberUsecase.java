package api.vitaport.health.healthmodule.usecases.employee;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.healthmodule.infra.repositories.IEmployeeRepository;
import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetEmployeeDataByRegistrationNumberUsecase {

    private final IEmployeeRepository employeeRepository;

    @Autowired
    public GetEmployeeDataByRegistrationNumberUsecase(IEmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Employee execute(String reg){
        try {
            return employeeRepository.findEmployeeByRegistration(reg);
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ,"cant get employee data by registration number");
        }
    }
}
