package api.vitaport.health.healthmodule.usecases.healthdata;

import api.vitaport.health.healthmodule.domain.models.healthdata.HealthData;
import api.vitaport.health.commonmodule.infra.exceptions.ErrorEnum;
import api.vitaport.health.healthmodule.infra.repositories.IHealthDataRepository;
import api.vitaport.health.commonmodule.infra.exceptions.CannotGetEntityDataException;
import api.vitaport.health.healthmodule.usecases.healthdata.dto.ReadHealthDataDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetLastEmployeeHealthDataUsecase {

    private final IHealthDataRepository healthDataRepository;

    @Autowired
    public GetLastEmployeeHealthDataUsecase(IHealthDataRepository healthDataRepository){
        this.healthDataRepository = healthDataRepository;
    }

    public HealthData execute(UUID employee_Id){
        HealthData healthData;
        try {
            return healthDataRepository.findLatestByEmployeeId(employee_Id);
        } catch (EntityNotFoundException | NoResultException e){
            throw new CannotGetEntityDataException(400, ErrorEnum.LBDQ,"cant get last employee health data by uuid");
        }
    }
}
