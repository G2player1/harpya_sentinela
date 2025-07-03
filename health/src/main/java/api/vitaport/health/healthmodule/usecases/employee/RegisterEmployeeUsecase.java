package api.vitaport.health.healthmodule.usecases.employee;

import api.vitaport.health.healthmodule.domain.models.employee.Employee;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.healthmodule.mappers.EmployeeMapper;
import api.vitaport.health.usermodule.domain.models.user.User;
import api.vitaport.health.healthmodule.infra.repositories.IEmployeeRepository;
import api.vitaport.health.healthmodule.usecases.employee.dto.RegisterEmployeeDTO;
import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import api.vitaport.health.healthmodule.usecases.employee.validations.IRegisterEmployeeValidation;
import api.vitaport.health.usermodule.usecases.user.GetUserDataUsecase;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegisterEmployeeUsecase {

    private final EmployeeMapper employeeMapper;
    private final IEmployeeRepository employeeRepository;
    private final List<IRegisterEmployeeValidation> registerEmployeeValidations;
    private final GetUserDataUsecase getUserDataUsecase;

    @Autowired
    public RegisterEmployeeUsecase(IEmployeeRepository employeeRepository,
                                   List<IRegisterEmployeeValidation> registerEmployeeValidations,
                                   GetUserDataUsecase getUserDataUsecase,
                                   EmployeeMapper employeeMapper){
        this.employeeRepository = employeeRepository;
        this.registerEmployeeValidations = registerEmployeeValidations;
        this.getUserDataUsecase = getUserDataUsecase;
        this.employeeMapper = employeeMapper;
    }

    @Transactional
    public Employee execute(RegisterEmployeeDTO registerEmployeeDTO) {
        registerEmployeeValidations.forEach(iRegisterEmployeeValidation -> iRegisterEmployeeValidation.validate(registerEmployeeDTO));
        try {
            Employee employee = employeeMapper.mapToEmployeeEntity(registerEmployeeDTO);
            User user = getUserDataUsecase.execute(registerEmployeeDTO.user_id());
            employee.setUser(user);
            return employeeRepository.save(employee);
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ,"cant get user by uuid");
        }
    }
}
