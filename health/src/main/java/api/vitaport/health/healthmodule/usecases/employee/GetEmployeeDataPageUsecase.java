package api.vitaport.health.healthmodule.usecases.employee;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.healthmodule.infra.repositories.IEmployeeRepository;
import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetEmployeeDataPageUsecase {

    private final IEmployeeRepository employeeRepository;

    @Autowired
    public GetEmployeeDataPageUsecase(IEmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Page<Employee> execute(Pageable pageable){
        try {
            return employeeRepository.findAll(pageable);
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ,"cant get page of employees");
        }
    }
}
