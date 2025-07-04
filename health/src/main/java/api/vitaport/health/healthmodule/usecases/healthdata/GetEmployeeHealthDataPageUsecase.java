package api.vitaport.health.healthmodule.usecases.healthdata;

import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.healthmodule.domain.models.healthdata.HealthData;
import api.vitaport.health.healthmodule.infra.repositories.IHealthDataRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetEmployeeHealthDataPageUsecase {

    private final IHealthDataRepository healthDataRepository;

    @Autowired
    public GetEmployeeHealthDataPageUsecase(IHealthDataRepository healthDataRepository){
        this.healthDataRepository = healthDataRepository;
    }


    public Page<HealthData> execute(UUID employeeId, Pageable pageable) {
        try {
            return healthDataRepository.findAllByEmployee(employeeId, pageable);
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ,"cant get employee's health data by employee_id");
        }
    }
}
